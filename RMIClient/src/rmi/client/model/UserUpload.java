package rmi.client.model;
import java.io.File;
import java.rmi.Naming;
import rmi.client.startup.RMIClient;
import static rmi.client.startup.RMIClient.URL;
import rmi.common.RMIInterface;
import rmi.client.net.UploadTCP;
import rmi.client.view.cmdLine;

public class UserUpload {
    
    
public static void userUploadProcess(String result) throws Exception{
    System.out.println(result);
    RMIInterface userOperation=(RMIInterface)Naming.lookup(URL); 
    cmdLine.println("Please input your file path: (if you want to quit upload process just type \"Quit\")");
    while(true){
        String userPath = cmdLine.getFilePath();
        String fileName = null;
        if (userPath.contains("/")){
        fileName = userPath.substring(userPath.lastIndexOf("/"), 
            userPath.length());
        fileName = fileName.substring(1,fileName.length());
        }
        if(userPath.equals("Quit")||userPath.equals("quit")){
            break;
        }
        else if (fileName == null){
            cmdLine.println("File doen't exist. Please enter a correct path!");
        }
        else if (result.contains(fileName)){
            cmdLine.println("File already exists. Please change your file name!");
        }
        else if(!new File(userPath).exists()){
            cmdLine.println("File doen't exist. Please enter a correct path!");
        }
        else{
            cmdLine.println("\nYour file is \"Public(Read only)\" or \"Public(All permission)\" or \"Private\"! Please type: ");

            while(true){
                //choose the permission of the file, the choice will be encapsulted into "@username#filename)<permission>"
                //and the server will extract the choice and permission by the punctuation
                String publicFLG =cmdLine.fileFeatureSele();
                if (publicFLG.equals("1")){
                    userOperation.uploadProcess("@"+RMIClient.USER_NAME+"#"+fileName+")"+"<public:read>");
                    break;
                }
                else if (publicFLG.equals("2"))
                {
                    userOperation.uploadProcess("@"+RMIClient.USER_NAME+"#"+fileName+")"+"<public:all>");
                    break;
                }
                else if (publicFLG.equals("3"))
                {
                    userOperation.uploadProcess("@"+RMIClient.USER_NAME+"#"+fileName+")"+"<private:all>");
                    break;
                }
                else{
                    cmdLine.println("Illegal input!");
                }
            }

            UploadTCP.userTCPUpload(userPath); //start a socket to transmit the file
            cmdLine.println("File upload success");
            break;
        }
    }
}
    
  
}
