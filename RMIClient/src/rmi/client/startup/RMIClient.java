package rmi.client.startup; 

import rmi.client.controller.FirstChoiceJudge;
import rmi.client.controller.UserOperationChoice;
import rmi.client.controller.UserInitial;

public class RMIClient { 
  //  public static String CLIENT_IP;
    public static String SERVER_IP;
    public static String URL; 
    public static String USER_NAME = null;
    public static String USER_NAME_TEMP = null;
    public static void main(String[] args) throws Exception { 
    boolean breakFLG = false;
    UserInitial.userInitial();
    while(true){
    //This cycle is for user to choose whether to login, register or quit.
    //After the process finishes, the breakFLG will be true and turn to 
    //the next cycle in order to choose the specific file service.
        while(true){
            breakFLG = FirstChoiceJudge.firstJudge();
            if (breakFLG){
                breakFLG = false;
                break;
            }
        }
    //This cycle is for user to choose specific file service.
    //If the breakFLG becomes true, it means the user choose to log out.
    //The client program will be stopped and quit.
        while(true){
            breakFLG = UserOperationChoice.operationJudge();
            if (breakFLG){
                breakFLG = false;
                break;
            }
        }

        }
        
      
        
        
        
    }
    public static String getCTX(String originalCTX,String firstSplit,String secondSplit){
        String resultCTX = originalCTX.substring(originalCTX.lastIndexOf(firstSplit), 
        originalCTX.lastIndexOf(secondSplit));
        resultCTX = resultCTX.substring(1,resultCTX.length());
        return resultCTX;
    }
} 