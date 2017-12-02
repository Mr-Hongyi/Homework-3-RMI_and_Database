/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi.server.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import rmi.server.controller.SystemInitial;
import rmi.server.net.ThreadTcpUpdate;

/**
 *
 * @author harry
 */

public class FileUpdate {
    public static String updateInitial(String userName){
        String result = null;
        String personalFile = null;
        String publicAll = null;
        System.out.println("Begin update for: "+userName);

        Connection conn = SystemInitial.getConn();
        String sql = "select * from UserInfo";
        PreparedStatement pstmt;
        try {
        pstmt = (PreparedStatement)conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next())
            {

                if(rs.getString(1).equals(userName))
                {
                    personalFile = "<Your personal files: [" + rs.getString(3)+"]>";
                }

            }
        rs = pstmt.executeQuery();
        while (rs.next()){
            if(rs.getString(1).equals("publicallpermission"))
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
    
     public static void tcpUpdate(String userFileName) throws Exception{
        new ThreadTcpUpdate(userFileName).start();
}
    
}
