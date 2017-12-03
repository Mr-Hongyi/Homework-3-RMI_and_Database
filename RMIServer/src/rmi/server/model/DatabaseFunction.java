package rmi.server.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import rmi.server.controller.SystemInitial;

public class DatabaseFunction {
    
    public static int dataInsert(UserInfo userInfo) {
        
    Connection conn = SystemInitial.getConn();
    int i = 0;
    String sql = "insert into UserInfo (userName,userPassword,userFileName) values(?,?,?)";
    PreparedStatement pstmt;
    try {
        
        pstmt = (PreparedStatement) conn.prepareStatement(sql); //get a PreparedStatement object to insert the parameters into UserInfo
        //set the index and value of the parameters
        pstmt.setString(1, userInfo.getUserName());
        pstmt.setString(2, userInfo.getUserPassword());
        pstmt.setString(3, userInfo.getUserFileName());
        i = pstmt.executeUpdate(); //return the number of updated parameters
        pstmt.close();
        conn.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return i;
}
    
public static int dataUpdate(UserInfo userInfo) {
    Connection conn = SystemInitial.getConn();
    int i = 0;
    String sql = "update UserInfo set userFileName='" + userInfo.getUserFileName() + "' where userName='" + userInfo.getUserName() + "'";
    PreparedStatement pstmt;
    try {
        pstmt = (PreparedStatement) conn.prepareStatement(sql);//create a PreparedStatement to update the userFileName in database userInfo
        i = pstmt.executeUpdate();//return the number of updated parameters
        pstmt.close();
        conn.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return i;
}
    
public static Integer dataBaseGetAll() {
    Connection conn = SystemInitial.getConn();
    String sql = "select * from UserInfo";
    PreparedStatement pstmt;
    try {
        pstmt = (PreparedStatement)conn.prepareStatement(sql);//select a PreparedStatement object to select data from userInfo
        ResultSet rs = pstmt.executeQuery();//
        int col = rs.getMetaData().getColumnCount();//get the colunm number of the data set
        System.out.println("============================");
        while (rs.next()) //if the database has data
        {
            for (int i = 1; i <= col; i++) //print the content of every content
            {
                System.out.print(rs.getString(i) + "\t");
                if ((i == 2) && (rs.getString(i).length() < 8)) {
                    System.out.print("\t");
                }
             }
            System.out.println("");
        }
            System.out.println("============================");
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

public static int dataDelete(String userName) {
    Connection conn = SystemInitial.getConn();
    int i = 0;
    String sql = "delete from UserInfo where userName='" + userName + "'";
    PreparedStatement pstmt;
    try {
        pstmt = (PreparedStatement) conn.prepareStatement(sql);//create a PreparedStatement object to delete the content in the database
        i = pstmt.executeUpdate(); //get the number of updated paremeters
        pstmt.close();
        conn.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return i;
}
}
