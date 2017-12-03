/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi.client.model;
import java.rmi.Naming;
import java.util.*;
import rmi.client.startup.RMIClient;
import static rmi.client.startup.RMIClient.URL;
import rmi.client.view.cmdLine;
import rmi.common.RMIInterface;

/**
 *
 * @author harry
 */
public class UserUnregister {
    public static String unregisterProcess() throws Exception{
        String result = null;
        Scanner sc = new Scanner(System.in);
        RMIInterface userOperation=(RMIInterface)Naming.lookup(URL);
        String userConfirm = cmdLine.userUnregisterCMD();
        while (true){
            if (userConfirm.equals("Y")||userConfirm.equals("N"))
            //if the user enters neither Y nor N, ask the user to enter again 
                    {
                        break;
                    }
            System.out.println("Please enter \"Y\" or \"N\" !");
            userConfirm = cmdLine.unregisterConf();
        }
        switch (userConfirm){
            case "Y":
            //if the user enters "Y", call the method operation system to delete 
            //the files and the document of the user and exit the system.
            {
                result = userOperation.operationSystem("(5@"+RMIClient.USER_NAME+"#");
                cmdLine.println(result);
                System.exit(0);
                break;
            }
            case "N" :
            //if the user enters "N", start the cycle again
            {
                result = "Please choose new operation.";
                break;
            }
        }
        return result;
    }
}
