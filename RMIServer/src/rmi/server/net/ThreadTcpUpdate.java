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

public class ThreadTcpUpdate extends Thread implements Runnable{
    private String userFileName;
    public ThreadTcpUpdate(String userFileName){
    this.userFileName = userFileName;
}
    
@Override
public void run() {
    try {
        String folderPath = null;
        String personalFile = null;
        String publicAll = null;
        String userName = getCTX(userFileName,"@","#");
        String fileName = getCTX(userFileName,"#",")");
        String publicFLG = getCTX(userFileName,"<",":");
        String readFLG = getCTX(userFileName,":",">");

        ServerSocket sSocket=new ServerSocket(10006);//create a ServerSocket object running on port 10006
        Socket s= sSocket.accept();//accept all the connection
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
        String fileSize1 = RMIServer.getFileSize(folderPath+fileName);//get the size of the file
        File file = new File(folderPath+fileName);
        
        //transmit the file in stream
        InputStream in = s.getInputStream();
        FileOutputStream fos = new FileOutputStream(file);
        byte[] buffile = new byte[2048];

        int len = 0;

        while ((len = in.read(buffile)) != -1)
        {
            fos.write(buffile, 0, len);
        }
        
        //update the information in the database
        String fileSize2 = RMIServer.getFileSize(folderPath+fileName);
        Connection conn = SystemInitial.getConn();
            String sql = "select * from UserInfo";
            PreparedStatement pstmt;
            
                try {
                    pstmt = (PreparedStatement)conn.prepareStatement(sql);//create a PreparedStatement object to select data from UserInfo
                    
                    if (publicFLG.equals("private"))
                    //if file type is private, update the file in the personalFile 
                    //column related to specific user
                    {
                        ResultSet rs = pstmt.executeQuery();
                        while (rs.next())//if the ResultSet has data
                        {
                            
                            if(rs.getString(1).equals(userName))
                            {
                                personalFile = rs.getString(3);
                            }
                            
                        }
                        personalFile = personalFile.replace(fileName+fileSize1+" ",fileName+fileSize2+" ");
                        DatabaseFunction.dataUpdate(new UserInfo(userName,"",personalFile));
                    }
                    else if (publicFLG.equals("public")&&readFLG.equals("all"))
                    //if file type is private, update the file in the publicfile
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
                         String oldUserName = RMIServer.getOldFileName(fileName, publicAll);
                        publicAll = publicAll.replace("\""+oldUserName+"\":"+fileName + fileSize1+" ", 
                                "\""+userName+"\":"+fileName + fileSize2 + " ");
                      DatabaseFunction.dataUpdate(new UserInfo("publicallpermission","",publicAll));
                    }
          
          
            } catch (SQLException e) {
        e.printStackTrace();
    }
        
        
        s.close();
        sSocket.close();
        
        
        
       System.out.println("Update success! "+userName+": "+fileName + fileSize2);

    } catch (IOException ex) {
    Logger.getLogger(ThreadTcpUpload.class.getName()).log(Level.SEVERE, null, ex);
    }
}
    
}
