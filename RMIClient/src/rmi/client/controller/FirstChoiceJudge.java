
package rmi.client.controller;

import rmi.client.model.UserChoice;
import rmi.client.view.cmdLine;

public class FirstChoiceJudge {
    
    public static boolean firstJudge() throws Exception{
        
        boolean breakFLG = false;
        
        String userAttempt = cmdLine.firstJudgeCMD();
        
        switch (userAttempt)
        {
            case "1" :{
                breakFLG = UserChoice.userLogin();
                break;
            }
            case "2" :{
                breakFLG = UserChoice.userRegister();
                break;
                
            }
            case "3" :{
                System.exit(0);   
            }
            default: {
                cmdLine.println("Illegal Input");
                    break;
            }
            
        }
         return breakFLG;
    }
    
}
