package rmi.server.model;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static rmi.server.controller.ServiceImpl.getCTX;
import rmi.server.controller.SystemInitial;
import rmi.server.startup.RMIServer;


public class FileDelete {
    public static String deleteInitial(String userName){
        String result = null;
        String personalFile = null;
        String publicAll = null;
        System.out.println("Begin delete for: "+userName);

        Connection conn = SystemInitial.getConn(); //connect with the sql database
        String sql = "select * from UserInfo";
        PreparedStatement pstmt;
        try {
        pstmt = (PreparedStatement)conn.prepareStatement(sql);//create a PreparedStatement object to select data from UserInfo
        ResultSet rs = pstmt.executeQuery();

        while (rs.next())//if the ResultSet has data
            {
            if(rs.getString(1).equals(userName))
            //if the first colunm of the ResultSet is the same as the userName, then return the
            //user's personal files' name
            {
                personalFile = "<Your personal files: [" + rs.getString(3)+"]>";
            }

            }
        rs = pstmt.executeQuery();
        while (rs.next()){
            if(rs.getString(1).equals("publicallpermission"))
            //if the first colunm of the ResultSet is the same as publicallpermission, then return the
            //all public files' name
            {
                publicAll ="{Public files(All Permission): [" + rs.getString(3)+"]}";
            }
        }
          
            result = personalFile + publicAll;
        } catch (SQLException e) {
        e.printStackTrace();
    }
        return result;
    }
    
    public static String fileDelete(String userfileName){
         /*
    Since the datagram of deleting the file will be encapsulated like "@+userName#fileName)<private:all>",
    after receiving the datagram, the server will extract the username, filename and authentication of the
    file.
    */
    
    String folderPath = null;
    String personalFile = null;
    String publicAll = null;

    String result =null;
    String userName = getCTX(userfileName,"@","#");
    String fileName = getCTX(userfileName,"#",")");
    String publicFLG = getCTX(userfileName,"<",":");
    String readFLG = getCTX(userfileName,":",">");


    if (publicFLG.equals("private"))
    //if the file is a private file, get the storage path of user's document
    {
        folderPath = RMIServer.FILE_STORAGE_PATH + userName+"/";
    }
    else if (publicFLG.equals("public")&&readFLG.equals("all"))
    //if the file is a public file with all authentication, the user also
    //has right to delete, then get the storage path of document storaging
    //the public file with all permission.
    {
        folderPath = RMIServer.FILE_PUBLIC_ALL;
    }
    String fileSize = RMIServer.getFileSize(folderPath+fileName);
    new File(folderPath+fileName).delete(); //delete the file from the storage document
    Connection conn = SystemInitial.getConn(); //connect with the database
    String sql = "select * from UserInfo"; 
    PreparedStatement pstmt; 

    try {
        pstmt = (PreparedStatement)conn.prepareStatement(sql); 
    //create a PreparedStatement object to send sql sentence to the mysql database

        if (publicFLG.equals("private"))
        {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())//if the database has information
            {

                if(rs.getString(1).equals(userName))
                //if the first colunmn of the ResultSet colunm is userName, 
                //then get the personalFile name from the third colunm
                {
                    personalFile = rs.getString(3);
                }

            }
            personalFile = personalFile.replace(fileName+fileSize+" ","");
            DatabaseFunction.dataUpdate(new UserInfo(userName,"",personalFile));
        }
        else if (publicFLG.equals("public")&&readFLG.equals("all")){                    
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) //if the database has information
            {

                if(rs.getString(1).equals("publicallpermission"))
                //if the content of the first colunm is publicallpermission,
                //then get the file base from the third colunm
                {
                    publicAll = rs.getString(3);
                }

            }
            //replace the file in the database
            String oldUserName = RMIServer.getOldFileName(fileName, publicAll);
            publicAll = publicAll.replace("\""+oldUserName+"\":"+fileName+fileSize+" ","");
            DatabaseFunction.dataUpdate(new UserInfo("publicallpermission","",publicAll));
        }

    result = "File delete success: "+ fileName + fileSize;
    } catch (SQLException e) {
    e.printStackTrace();
}
                return result;
    }
}
