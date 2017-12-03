package rmi.server.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import rmi.server.controller.SystemInitial;
import rmi.server.net.ThreadTcpUpdate;

public class FileUpdate {
    public static String updateInitial(String userName){
        //Since the client can only update the personal file and public file with all permission,
        //only personal file and public file with all permission should be search from the 
        //database and display to the client
        String result = null;
        String personalFile = null;
        String publicAll = null;
        System.out.println("Begin update for: "+userName);

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
                    personalFile = "<Your personal files: [" + rs.getString(3)+"]>";
                }

            }
        rs = pstmt.executeQuery();
        while (rs.next()){
            if(rs.getString(1).equals("publicallpermission"))
            //if the first column of the ResultSet is the same as publicallpermission, 
            //return all public files' name and encapsulate the file names
            {
                publicAll ="{Public files(All Permission): [" + rs.getString(3)+"]}";
            }
        }
        //the file name of personalFile and publicAll file will be
        //listed and display to the client
        result = personalFile + publicAll;

        } catch (SQLException e) {
        e.printStackTrace();
    }
        return result;
    }
    
     public static void tcpUpdate(String userFileName) throws Exception{
        new ThreadTcpUpdate(userFileName).start();
}
    
}
