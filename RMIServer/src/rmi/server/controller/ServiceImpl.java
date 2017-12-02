
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
            ResultSet rs = pstmt.executeQuery();
            int rowCount = 0;  
            while(rs.next()) {  
                rowCount++;  
            }
            if(rowCount<1){
                returnWord = "No user in the Database. Please create an account";
                break;
            }
            returnWord = LoginChoice.userLogin(userInput);
            break;
            } catch (SQLException e) {
             e.printStackTrace();
        }
        }
        case "2" :{
            returnWord = LoginChoice.userRegister(userInput);
            break;  
        }

        
    }
    return String.format(returnWord);
} 


@Override 
public String operationSystem(String userOperation) throws RemoteException { 
    String returnWord = null;
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
        FileUpload.tcpUpload(userFileName);
    } catch (Exception ex) {
        Logger.getLogger(ServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
}


@Override
public void downloadProcess(String userFileName) throws RemoteException{
    
    try {
        FileDownload.tcpDownload(userFileName);
    } catch (Exception ex) {
        Logger.getLogger(ServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
}


@Override
public void updateProcess(String userFileName) throws RemoteException{
    
    try {
        FileUpdate.tcpUpdate(userFileName);
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