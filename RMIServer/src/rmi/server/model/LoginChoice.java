package rmi.server.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.*;
import static rmi.server.controller.ServiceImpl.getCTX;
import rmi.server.controller.SystemInitial;
import rmi.server.startup.RMIServer;

public class LoginChoice {
    public static String userLogin(String userInput){
    String returnWord = null;
    String userName = getCTX(userInput,"@","#");//extract the user name and password by the punctuations
    System.out.println("Begin operation 1 for: "+userName);
    String userPass = getCTX(userInput,"#",")");

    Connection conn = SystemInitial.getConn();
    String sql = "select * from UserInfo";
    PreparedStatement pstmt;
    try {
    pstmt = (PreparedStatement)conn.prepareStatement(sql);//create a PreparedStatement object to select data from UserInfo
    ResultSet rs = pstmt.executeQuery();

    while (rs.next())//if the ResultSet has data
        {                    
            if(rs.getString(1).equals(userName)&&rs.getString(2).equals(userPass))
            //if the userName and password matches the result from the ResultSet at the same time,
            //display the successful message. Otherwise, display the failure message to the 
            //client                   
            {
                returnWord = "Sucess! Welcome user: " + userName + "^";
                System.out.println(userName+": login sucess");
                DatabaseFunction.dataBaseGetAll();
                break;
            }
            returnWord = "Wrong user Name or Password";
        }
    } catch (SQLException e) {
     e.printStackTrace();
    }
            return returnWord;
    }
    
    public static String userRegister(String userInput){
        
        String returnWord = null;
        boolean createFLG = true;
            String userName = getCTX(userInput,"@","#");//extract the user name and password by the punctuations
            System.out.println("Begin operation 2 for: "+userName);
            String userPass = getCTX(userInput,"#",")");
            Connection conn = SystemInitial.getConn();//connect with the database
            String sql = "select * from UserInfo";
            PreparedStatement pstmt;
            try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);//create a PreparedStatement object to select data from UserInfo
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next())
            //if the ResultSet has data, check whether there is a match in the ResultSet
            //If there is a match, it means the user exists.
                {                   
                    if(rs.getString(1).equals(userName))
                    {
                        returnWord = "Wrong! User exists!" ;
                        createFLG = false;
                        break;
                    }
                    
                }
            if (createFLG)
            //if the userName has not been created before, then insert the username
            //and password to the database
            {
                DatabaseFunction.dataInsert(new UserInfo(userName, userPass, ""));
                new File(RMIServer.FILE_STORAGE_PATH+userName+"/").mkdir();
                returnWord = "Create user Sucessfully! Welcome user: " + userName + "^";
                
                DatabaseFunction.dataBaseGetAll();
            }
            } catch (SQLException e) {
        e.printStackTrace();
    }
        return returnWord;
        
    }
    
    public static String userUnregister(String userInput){
        String returnWord = null;
        String userName = getCTX(userInput,"@","#");//extract the userName
        DatabaseFunction.dataDelete(userName);//delete the username and its information from the database
        deleteDir(new File(RMIServer.FILE_STORAGE_PATH+userName));//delete the file and document storing on the server
            returnWord = "Sucess! User Removed: " + userName;
            System.out.println(userName+": remove sucess");
            return returnWord;
    }
    
    private static void deleteDir(File dir) {
        //iteratively delete the file in the directory
        if (dir.isDirectory()) {
            String[] children = dir.list();//get all the files in the directory
            for (int i=0; i<children.length; i++) {
                deleteDir(new File(dir, children[i])); 
            }
        }
        dir.delete();//after delete all the file storing in the directory, delete
        //the directory itself
    }
    
}
