
package rmi.client.model;

import java.rmi.Naming;
import static rmi.client.startup.RMIClient.URL;
import rmi.common.RMIInterface;
import rmi.client.startup.RMIClient;
import rmi.client.view.cmdLine;
/**
 *
 * @author harry
 */
public class UserChoice {

    public static boolean userLogin() throws Exception{
        boolean breakFLG = false;
        RMIInterface userOperation=(RMIInterface)Naming.lookup(URL); //bind to specific remote interface
        String sendCTX = cmdLine.userLoginCMD();
        String result=userOperation.loginSystem(sendCTX);
        //The result will be get from the server, if the result contains "^", 
        //which means the user login to the system successfully.
        //Set breakFLG true to jump out of the cycle in function firstjudge
        if (result.contains("^")){
            breakFLG = true;
            result = result.replace("^", "");
            RMIClient.USER_NAME = RMIClient.USER_NAME_TEMP;
        }
        cmdLine.println(result);
        return breakFLG;
    }
    
    public static boolean userRegister() throws Exception{
        boolean breakFLG = false;
        RMIInterface userOperation=(RMIInterface)Naming.lookup(URL); //bind to specific remote interface
        String sendCTX = cmdLine.userRegisterCMD();
        String result=userOperation.loginSystem(sendCTX);
        if (result.contains("^"))
        //The result will be get from the server, if the result contains "^", 
        //which means the user register to the system successfully.
        //Set breakFLG true to jump out of the cycle in function firstjudge
        {
            breakFLG = true;
            RMIClient.USER_NAME = RMIClient.USER_NAME_TEMP;
            result = result.replace("^", "");
        }
        cmdLine.println(result);
        return breakFLG;
    }
    
}
