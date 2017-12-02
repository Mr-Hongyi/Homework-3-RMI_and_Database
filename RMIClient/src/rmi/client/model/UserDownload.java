/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi.client.model;

import java.rmi.Naming;
import rmi.client.net.DownloadTCP;
import rmi.client.startup.RMIClient;
import static rmi.client.startup.RMIClient.URL;
import rmi.client.view.cmdLine;
import rmi.common.RMIInterface;
/**
 *
 * @author harry
 */
public class UserDownload {
    public static void userDownloadProcess(String result) throws Exception{
        String personalFile =RMIClient.getCTX(result, "<", ">");
        String publicAll = RMIClient.getCTX(result, "{", "}");
        String publicRead =RMIClient.getCTX(result, "!", "?");
        String fileName = null;
        cmdLine.println(personalFile+"\n"+publicAll+"\n"+publicRead);
        RMIInterface userOperation=(RMIInterface)Naming.lookup(URL); 
        cmdLine.println("Please input the file you want to download: "
                + "(if you want to quit download process just type \"Quit\")");
    
        while(true){
            fileName = cmdLine.getFileName();

            if(fileName.equals("Quit")||fileName.equals("quit")){
                break;
            }
            else if (personalFile.contains(fileName)){
                userOperation.downloadProcess("@"+RMIClient.USER_NAME+"#"+fileName+")"+"<private:all>");
                DownloadTCP.userTCPDownload(fileName);
                cmdLine.println("File download success");
                break;
            }
            else if (publicAll.contains(fileName)){
                userOperation.downloadProcess("@"+RMIClient.USER_NAME+"#"+fileName+")"+"<public:all>");
                DownloadTCP.userTCPDownload(fileName);
                cmdLine.println("File download success");
                break;
            }
            else if (publicRead.contains(fileName)){
                userOperation.downloadProcess("@"+RMIClient.USER_NAME+"#"+fileName+")"+"<public:read>");
                DownloadTCP.userTCPDownload(fileName);
                cmdLine.println("File download success");
                break;
            }

            else{
                
                System.out.println("File dosen't exists!");
                
                }
            }
        } 
}


    

