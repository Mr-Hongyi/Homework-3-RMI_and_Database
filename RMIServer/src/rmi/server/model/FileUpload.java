package rmi.server.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import rmi.server.controller.SystemInitial;
import rmi.server.net.ThreadTcpUpload;

public class FileUpload {
    public static String uploadInitial(String userName){
    String result = null;
    String personalFile = null;
    String publicAll = null;
    String publicRead = null;
    System.out.println("Begin upload for: "+userName);

    Connection conn = SystemInitial.getConn();
    String sql = "select * from UserInfo";
    PreparedStatement pstmt;
    try {
    pstmt = (PreparedStatement)conn.prepareStatement(sql);//create a PreparedStatement object to select data from UserInfo
    ResultSet rs = pstmt.executeQuery();

    while (rs.next())//if the ResultSet has data
        {                  
            if(rs.getString(1).equals(userName))
            //if the first column of the ResultSet is the same as the userName, 
            //return all the personal files' name and encapsulate the file names
            {
                personalFile = "Your personal files: [" + rs.getString(3)+"]\n";
            }

        }
    rs = pstmt.executeQuery();
    while (rs.next())//if the ResultSet has data
    {
        if(rs.getString(1).equals("publicallpermission"))
        //if the first column of the ResultSet is the same as publicallpermission, 
        //return all public files' name and encapsulate the file names
        {
            publicAll ="Public files(All Permission): [" + rs.getString(3)+"]\n";
        }
    }
    rs = pstmt.executeQuery();
    while (rs.next())//if the ResultSet has data
    {
        if(rs.getString(1).equals("publicreadonly"))
        //if the first column of the ResultSet is the same as publicreadonly, 
        //return all read only files' name and encapsulate the file names
        {
            publicRead ="Public files(Read Only): [" + rs.getString(3)+"]";
        }
    }
    result = personalFile + publicAll + publicRead;
    } catch (SQLException e) {
    e.printStackTrace();
    }
        return result;
    }
    
    public static void tcpUpload(String userFileName) throws Exception{
        new ThreadTcpUpload(userFileName).start();
}
}
