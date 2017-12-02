package rmi.client.startup; 

import rmi.client.controller.FirstChoiceJudge;
import rmi.client.controller.UserOperationChoice;
import rmi.client.controller.UserInitial;

public class RMIClient { 
    public static String SERVER_IP;
    public static String URL; 
    public static String USER_NAME = null;
    public static String USER_NAME_TEMP = null;
    public static void main(String[] args) throws Exception { 
        boolean breakFLG = false;
        UserInitial.userInitial();
        while(true){
        
            while(true){
                breakFLG = FirstChoiceJudge.firstJudge();
                if (breakFLG){
                    breakFLG = false;
                    break;
                }
            }
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