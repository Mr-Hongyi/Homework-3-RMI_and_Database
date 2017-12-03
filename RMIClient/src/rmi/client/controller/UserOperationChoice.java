package rmi.client.controller;

import rmi.client.model.UserDelete;
import rmi.client.model.UserDownload;
import rmi.client.model.UserUnregister;
import rmi.client.model.UserUpdate;
import rmi.client.model.UserUpload;
import java.rmi.Naming;
import rmi.client.startup.RMIClient;
import static rmi.client.startup.RMIClient.URL;
import rmi.client.view.cmdLine;
import rmi.common.RMIInterface;

public class UserOperationChoice {
    
    public static boolean operationJudge() throws Exception{
        boolean breakFLG = false;
        RMIInterface userOperation=(RMIInterface)Naming.lookup(URL); 
        String userAttempt = cmdLine.operationJudgeCMD();
        //The user's choice will be encapsulted into "(the number of service@username#"
        //and sent to the server to extract the message and deal with the request. 
        //Then the server will send the result back.
        switch (userAttempt)
       {
            case "0" :{
                String result = null;
                result = userOperation.operationSystem("(0@"+RMIClient.USER_NAME+"#");
                cmdLine.println("Your file list: \n"+result);
                while(true){
                    String quitFLG = cmdLine.quitFLG();
                    if (quitFLG.equals("quit")||quitFLG.equals("Quit"))
                        break;
                }
                break;
            }
            
            case "1" :{
                String result = null;
                result = userOperation.operationSystem("(1@"+RMIClient.USER_NAME+"#");
                UserUpload.userUploadProcess(result);
                
                break;
            }
            case "2" :{
                String result = null;
                result = userOperation.operationSystem("(2@"+RMIClient.USER_NAME+"#");
                UserDownload.userDownloadProcess(result);
                
                break;
                
            }
             case "3" :{
                String result = null;
                result = userOperation.operationSystem("(3@"+RMIClient.USER_NAME+"#");
                UserUpdate.userUpdateProcess(result);
                break;
                
            }
              case "4" :{
                String result = null;
                result = userOperation.operationSystem("(4@"+RMIClient.USER_NAME+"#");
                UserDelete.userDeleteProcess(result);
                break;
                
            }
            case "5" :{
                String result = null;
                result = UserUnregister.unregisterProcess();
                cmdLine.println(result);
                break;
           
            }
            case "6" :{
                String result = null;
                result = RMIClient.USER_NAME+" log out!";
                RMIClient.USER_NAME = null;
                RMIClient.USER_NAME_TEMP = null;
                cmdLine.println(result);
                breakFLG = true;
                break;
            }
             default: {
                cmdLine.println("Illegal input");
                        break;
            }
             
            
    }
        return breakFLG; 
    
}
}
