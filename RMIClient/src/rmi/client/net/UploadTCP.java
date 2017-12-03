package rmi.client.net;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.Socket;
import rmi.client.startup.RMIClient;

public class UploadTCP {
/*
This function is to start a socket to transmit the file. The socket is running on
port 10006. The file will be transmitted in stream. 
*/
    public static void userTCPUpload(String userFilePath) throws Exception{
    Socket s = new Socket(RMIClient.SERVER_IP,10006);
    File file = new File(userFilePath);
    FileInputStream fis = new FileInputStream(file);
    OutputStream out = s.getOutputStream();
    byte[] buf = new byte[2048];
        int len = 0;

        while ((len = fis.read(buf)) != -1)
                {
            out.write(buf, 0, len);
        }
        s.shutdownOutput();
    s.close();
    fis.close();
    
    } 
}
