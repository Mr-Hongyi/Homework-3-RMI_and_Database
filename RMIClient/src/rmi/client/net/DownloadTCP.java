/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi.client.net;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.Socket;
import rmi.client.startup.RMIClient;
import rmi.client.view.cmdLine;
/**
 *
 * @author harry
 */
public class DownloadTCP {
       
public static void userTCPDownload(String fileName) throws Exception{
    
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
