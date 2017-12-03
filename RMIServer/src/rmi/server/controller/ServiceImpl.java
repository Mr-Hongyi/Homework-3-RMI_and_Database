package rmi.server.controller;

import java.rmi.RemoteException; 
import java.rmi.server.UnicastRemoteObject; 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import rmi.common.RMIInterface; 
import rmi.server.model.FileDelete;
import rmi.server.model.FileUpload;
import rmi.server.model.LoginChoice;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmi.server.model.FileDownload;
import rmi.server.model.FileList;
import rmi.server.model.FileUpdate;

public class ServiceImpl extends UnicastRemoteObject implements RMIInterface{ 

    
public ServiceImpl() throws RemoteException { 
} 

@Override 
public String loginSystem(String userInput) throws RemoteException { 
// TODO Auto-generated method stub 
    
    String userChoice = getCTX(userInput,"(","@");
    String returnWord = null;
    switch (userChoice)
    {
        case "1" :{
            try{
            Connection conn = SystemInitial.getConn();
            String sql = "select * from UserInfo";
            PreparedStatement pstmt;
        
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();//execute query to search whether the database has stored user information
            int rowCount = 0;  
            while(rs.next()) //if the pointer can be moved to the next line, which means the database has stored user information, return true
            {  
                rowCount++;  
            }
            if(rowCount<1)//if rowCount is smaller than 1, which means the database has stored no information, then ask the client to create an account
            {
                returnWord = "No user in the Database. Please create an account";
                break;
            }
            //otherwise, call the function userLogin to login to the system
            returnWord = LoginChoice.userLogin(userInput);
            break;
            } catch (SQLException e) {
             e.printStackTrace();
        }
        }
        case "2" :{
            //call function userRegister to add a new user information to the database
            returnWord = LoginChoice.userRegister(userInput);
            break;  
        }

        
    }
    return String.format(returnWord);
} 


@Override 
public String operationSystem(String userOperation) throws RemoteException { 
    String returnWord = null;
    //Since the datagram from the user is encapsulated like "(the number of service@username#",
    //first the server extracts the userChoice and username by the punctuations.
    String userChoice = getCTX(userOperation,"(","@");
    String userName = getCTX(userOperation,"@","#");
    switch (userChoice)
    {
        case "0" :{
            returnWord = null;
            returnWord =FileList.getFileList(userName);
            break;
        }
        case "1" :{
            returnWord = null;
            returnWord =FileUpload.uploadInitial(userName);
            break;
        }
        case "2" :{
            returnWord = null;
            returnWord =FileDownload.downloadInitial(userName);
            break;
        }
        case "3" :{
            returnWord = null;
            returnWord =FileUpdate.updateInitial(userName);
            break; 
        }
        case "4" :{
            returnWord = null;
            returnWord =FileDelete.deleteInitial(userName);
            break;  
        }
       case "5" :{
            returnWord = LoginChoice.userUnregister(userOperation);
            break;
        } 
    }
    return String.format(returnWord);
}

@Override
public void uploadProcess(String userFileName) throws RemoteException{
    
    try {
        FileUpload.tcpUpload(userFileName); //start a socket by calling the function tcpUpload to receive the file
    } catch (Exception ex) {
        Logger.getLogger(ServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
}


@Override
public void downloadProcess(String userFileName) throws RemoteException{
    
    try {
        FileDownload.tcpDownload(userFileName); //start a socket by calling the function tcpDownload to transfer the file to the client
    } catch (Exception ex) {
        Logger.getLogger(ServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
}


@Override
public void updateProcess(String userFileName) throws RemoteException{
    
    try {
        FileUpdate.tcpUpdate(userFileName); //start a socket by calling the function tcpUpdate to receive the updated file
    } catch (Exception ex) {
        Logger.getLogger(ServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
}


@Override 
public String deleteProcess(String userfileName) throws RemoteException {
    String result = FileDelete.fileDelete(userfileName);
        return result;
   
} 

public static String getCTX(String originalCTX,String firstSplit,String secondSplit){
        String resultCTX = originalCTX.substring(originalCTX.lastIndexOf(firstSplit), 
        originalCTX.lastIndexOf(secondSplit));
        resultCTX = resultCTX.substring(1,resultCTX.length());
        return resultCTX;
    }

} 