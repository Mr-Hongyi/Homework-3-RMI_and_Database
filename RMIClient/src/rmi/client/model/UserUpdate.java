
package rmi.client.model;

import java.rmi.Naming;
import java.io.*;
import rmi.client.net.UpdateTCP;
import rmi.client.startup.RMIClient;
import static rmi.client.startup.RMIClient.URL;
import rmi.client.view.cmdLine;
import rmi.common.RMIInterface;

/**
 *
 * @author harry
 */
public class UserUpdate {
    public static void userUpdateProcess(String result) throws Exception{
        String personalFile =RMIClient.getCTX(result, "<", ">");
        String publicAll = RMIClient.getCTX(result, "{", "}");
        String fileName = null;
        String userPath=null;
        cmdLine.println(personalFile+"\n"+publicAll);
        RMIInterface userOperation=(RMIInterface)Naming.lookup(URL); 
        cmdLine.println("Please input the file path you want to update: (if you want to quit update process just type \"Quit\")");
        while(true){
            userPath = cmdLine.getFilePath();
            if (userPath.contains("/")){
            fileName = userPath.substring(userPath.lastIndexOf("/"), 
                userPath.length());
            fileName = fileName.substring(1,fileName.length());
            }

        if(userPath.equals("Quit")||userPath.equals("quit")){
            break;
        }
        else if (personalFile.contains(fileName)&&new File(userPath).exists()){
            userOperation.updateProcess("@"+RMIClient.USER_NAME+"#"+fileName+")"+"<private:all>");
            UpdateTCP.userTCPUpdate(userPath);
            cmdLine.println("File update success");
            break;
        }
        else if (publicAll.contains(fileName)&&new File(userPath).exists()){
            userOperation.updateProcess("@"+RMIClient.USER_NAME+"#"+fileName+")"+"<public:all>");
            UpdateTCP.userTCPUpdate(userPath);
            cmdLine.println("File update success");
            break;
        }


        else{
            cmdLine.println("File dosen't exists! Please type a correct path!");
        }
        }   



    }

}
