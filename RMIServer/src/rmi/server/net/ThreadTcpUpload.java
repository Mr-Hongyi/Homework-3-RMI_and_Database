package rmi.server.net;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static rmi.server.controller.ServiceImpl.getCTX;
import rmi.server.controller.SystemInitial;
import rmi.server.model.DatabaseFunction;
import rmi.server.model.UserInfo;
import rmi.server.startup.RMIServer;

public class ThreadTcpUpload extends Thread implements Runnable{
    private String userFileName;
    public ThreadTcpUpload(String userFileName){
    this.userFileName = userFileName;
}
    
@Override
public void run() {
    try {
        ServerSocket sSocket=new ServerSocket(10006);//create a ServerSocket object running on port 10006
        Socket s= sSocket.accept();//accept all the connection
        String folderPath = null;
        String personalFile = null;
        String publicAll = null;
        String publicRead = null;

        String userName = getCTX(userFileName,"@","#");
        String fileName = getCTX(userFileName,"#",")");
        String publicFLG = getCTX(userFileName,"<",":");
        String readFLG = getCTX(userFileName,":",">");


        if (publicFLG.equals("private"))
        //if the file type is private, get the path of private directory
        {
            folderPath = RMIServer.FILE_STORAGE_PATH + userName+"/";
        }
        else if (publicFLG.equals("public")&&readFLG.equals("all"))
        //if the file type is public with all authentication, get the path of 
        //public file with all authentication directory
        {
            folderPath = RMIServer.FILE_PUBLIC_ALL;
        }
        else if(publicFLG.equals("public")&&readFLG.equals("read"))
        //if the file type is public with all authentication, get the path of 
        //read only public file directory
        {
            folderPath = RMIServer.FILE_PUBLIC_READ;
        }
        File file = new File(folderPath+fileName);//create a file object by the file path
        
        //transmit the file in stream
        InputStream in = s.getInputStream();
        FileOutputStream fos = new FileOutputStream(file);
        byte[] buffile = new byte[2048];

        int len = 0;

        while ((len = in.read(buffile)) != -1)
        {
            fos.write(buffile, 0, len);
        }
        
        //add the information in the database
        String fileSize = RMIServer.getFileSize(folderPath+fileName);
        Connection conn = SystemInitial.getConn();
        String sql = "select * from UserInfo";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);//create a PreparedStatement object to select data from UserInfo

            if (publicFLG.equals("private"))
            //if file type is private, add the file in the personalFile 
            //column related to specific user
            {
                ResultSet rs = pstmt.executeQuery();
                while (rs.next())
                {
                    if(rs.getString(1).equals(userName))
                    {
                        personalFile = rs.getString(3);
                    }

                }
                personalFile = personalFile + fileName+ fileSize + " ";
                DatabaseFunction.dataUpdate(new UserInfo(userName,"",personalFile));
            }
            else if (publicFLG.equals("public")&&readFLG.equals("all"))
            //if file type is private, add the file in the publicfile
            //column related to publicllpermission
            {                    
                ResultSet rs = pstmt.executeQuery();
                while (rs.next())
                {

                    if(rs.getString(1).equals("publicallpermission"))
                    {
                        publicAll = rs.getString(3);
                    }

                }
                publicAll = publicAll + "\""+userName+"\":"+ fileName+ fileSize + " ";
                DatabaseFunction.dataUpdate(new UserInfo("publicallpermission","",publicAll));
            }
            else if (publicFLG.equals("public")&&readFLG.equals("read"))
            //if file type is read only, add the file in the publicfile
            //column related to publicreadonly
            {
                ResultSet rs = pstmt.executeQuery();
                while (rs.next())
                {
                    if(rs.getString(1).equals("publicreadonly"))
                    {
                        publicRead = rs.getString(3);
                    }
                }
                publicRead = publicRead +"\""+userName+"\":"+ fileName + fileSize + " ";
                DatabaseFunction.dataUpdate(new UserInfo("publicreadonly","",publicRead));
            }
            System.out.println("Upload success! "+userName+": "+fileName +fileSize);
            s.close();
            sSocket.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } catch (IOException ex) {
    Logger.getLogger(ThreadTcpUpload.class.getName()).log(Level.SEVERE, null, ex);
    }
}
    
}
