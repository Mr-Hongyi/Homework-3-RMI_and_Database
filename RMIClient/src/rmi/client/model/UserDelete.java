package rmi.client.model;

import java.rmi.Naming;
import rmi.client.startup.RMIClient;
import static rmi.client.startup.RMIClient.URL;
import rmi.client.view.cmdLine;
import rmi.common.RMIInterface;

public class UserDelete {
    
public static void userDeleteProcess(String result) throws Exception{
    /*
    After the server return the file name separated into different types, the
    client will first bind to a remote interface. Since the user only have authentication
    to delete his private file and public file with all authentication, only these
    two type will be displayed to the user.
    */
    String personalFile =RMIClient.getCTX(result, "<", ">");
    String publicAll = RMIClient.getCTX(result, "{", "}");
    cmdLine.println(personalFile+"\n"+publicAll);
    RMIInterface userOperation=(RMIInterface)Naming.lookup(URL); 
    cmdLine.println("Please input your filename: (if you want to quit delete process just type \"Quit\")");

    while(true){
        String fileName = cmdLine.getFileName();
        //The user can quit the process anytime, so if the user enters Quit or quit, 
        //then exit the download process.
        if(fileName.equals("Quit")||fileName.equals("quit")){
            break;
        }
        //If the file is a personal file, then encapsulte the file name and
        //send back to the server to start the delete program.
        else if (personalFile.contains(fileName)){
            result = userOperation.deleteProcess("@"+RMIClient.USER_NAME+"#"+fileName+")"+"<private:all>");
            cmdLine.println(result);
            break;
        }
        //If the file is a public file with all authentication, then encapsulte 
        //the file name and send back to the server to start the delete program.
        else if (publicAll.contains(fileName)){
            result = userOperation.deleteProcess("@"+RMIClient.USER_NAME+"#"+fileName+")"+"<public:all>");
            cmdLine.println(result);
            break;
        }


                else{
                    cmdLine.println("File dosen't exists! Please type a file name!");
                }
            }
    }
}
    

