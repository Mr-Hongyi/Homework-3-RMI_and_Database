
package rmi.client.controller;

import rmi.client.startup.RMIClient;
import static rmi.client.startup.RMIClient.URL;
import rmi.client.view.cmdLine;

public class UserInitial {
    
    public static void userInitial(){
        cmdLine.userInitialCMD();
        URL ="rmi://"+RMIClient.SERVER_IP+":9999/rmi.server.controller.ServiceImpl";
    }
    
}
