package rmi.client.model;

import java.rmi.Naming;
import rmi.client.net.DownloadTCP;
import rmi.client.startup.RMIClient;
import static rmi.client.startup.RMIClient.URL;
import rmi.client.view.cmdLine;
import rmi.common.RMIInterface;

public class UserDownload {
    public static void userDownloadProcess(String result) throws Exception{
        /*
        Since the user can download three types of the file, which are private 
        file that belongs to the client himself, public file that can be accessed 
        by all clients and public file that can only be read.
        The name of these three kinds of files will be encapsulted into different
        punctuation. After receiving the result, the client firstly extract the file
        name by the punctuation and show all the file name to the user.
        */
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

            //The user can quit the process anytime, so if the user enters Quit or quit, 
            //then exit the download process.
            if(fileName.equals("Quit")||fileName.equals("quit")){
                break;
            }
            //If the file is a personal file, then start a socket to download the file from the private
            //document in the server.
            else if (personalFile.contains(fileName)){
                userOperation.downloadProcess("@"+RMIClient.USER_NAME+"#"+fileName+")"+"<private:all>");
                DownloadTCP.userTCPDownload(fileName);
                cmdLine.println("File download success");
                break;
            }
            //If the file is a public file with all authentication, then start a 
            //socket to download the file from the public file with all authentication 
            //document in the server.
            else if (publicAll.contains(fileName)){
                userOperation.downloadProcess("@"+RMIClient.USER_NAME+"#"+fileName+")"+"<public:all>");
                DownloadTCP.userTCPDownload(fileName);
                cmdLine.println("File download success");
                break;
            }
            //If the file is a read only file, then start a socket to download the file from the public
            //file with read only authentication document in the server.
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


    

