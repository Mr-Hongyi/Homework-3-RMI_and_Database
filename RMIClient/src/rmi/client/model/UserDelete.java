/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi.client.model;

import java.rmi.Naming;
import java.util.Scanner;
import rmi.client.startup.RMIClient;
import static rmi.client.startup.RMIClient.URL;
import rmi.client.view.cmdLine;
import rmi.common.RMIInterface;

/**
 *
 * @author harry
 */
public class UserDelete {
    
public static void userDeleteProcess(String result) throws Exception{
    String personalFile =RMIClient.getCTX(result, "<", ">");
    String publicAll = RMIClient.getCTX(result, "{", "}");
    cmdLine.println(personalFile+"\n"+publicAll);
    RMIInterface userOperation=(RMIInterface)Naming.lookup(URL); 
    cmdLine.println("Please input your filename: (if you want to quit delete process just type \"Quit\")");

    while(true){
        String fileName = cmdLine.getFileName();
        if(fileName.equals("Quit")||fileName.equals("quit")){
            break;
        }
        else if (personalFile.contains(fileName)){
            result = userOperation.deleteProcess("@"+RMIClient.USER_NAME+"#"+fileName+")"+"<private:all>");
            cmdLine.println(result);
            break;
        }
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
    

