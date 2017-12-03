package rmi.client.net;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.Socket;
import rmi.client.startup.RMIClient;
import rmi.client.view.cmdLine;

public class DownloadTCP {
       
public static void userTCPDownload(String fileName) throws Exception{
/*
This function is to start a socket to download the file. The storage path is chosen
by the user. The function will first check whether the path exists. If the path exists,
then start a socket to download the file. The socket is running on port 10006. 
The file will be transmitted in stream.
After the transmission complete, the socket and the fileoutputstream will be closed.   
*/       
    String userFilePath = cmdLine.enterStoragePath();
    Socket s = new Socket(RMIClient.SERVER_IP,10006);
    File file = new File(userFilePath+"/"+fileName);
    InputStream in = s.getInputStream();

    FileOutputStream fos = new FileOutputStream(file);

    byte[] buffile = new byte[2048];

    int len = 0;

    while ((len = in.read(buffile)) != -1) 
        {
            fos.write(buffile, 0, len);
        }

    fos.close();
    s.close();

}
}
