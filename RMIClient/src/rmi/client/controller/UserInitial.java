
package rmi.client.controller;

import rmi.client.startup.RMIClient;
import static rmi.client.startup.RMIClient.URL;
import rmi.client.view.cmdLine;

public class UserInitial {
    
    public static void userInitial(){
        /*
        Since the server's ip address is not fixed, the client should enter the
        ip address every time to connect with specific server. Also, the url of 
        the remote host can be prepared to connect with the remote interface.
        */
        cmdLine.userInitialCMD();
        URL ="rmi://"+RMIClient.SERVER_IP+":9999/rmi.server.controller.ServiceImpl";
    }
    
}
