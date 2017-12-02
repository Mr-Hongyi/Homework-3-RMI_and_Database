/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi.server.net;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.logging.Level;
import java.util.logging.Logger;
import static rmi.server.controller.ServiceImpl.getCTX;

import rmi.server.startup.RMIServer;

/**
 *
 * @author harry
 */
public class ThreadTcpDownload extends Thread implements Runnable{
    private String userFileName;
    public ThreadTcpDownload(String userFileName){
    this.userFileName = userFileName;
}   
    
    @Override
public void run() {
    try {
        String folderPath = null;

        String userName = getCTX(userFileName,"@","#");
        String fileName = getCTX(userFileName,"#",")");
        String publicFLG = getCTX(userFileName,"<",":");
        String readFLG = getCTX(userFileName,":",">");

        ServerSocket sSocket=new ServerSocket(10006);
        Socket s= sSocket.accept();
        if (publicFLG.equals("private")){
            folderPath = RMIServer.FILE_STORAGE_PATH + userName+"/";
        }
        else if (publicFLG.equals("public")&&readFLG.equals("all")){
            folderPath = RMIServer.FILE_PUBLIC_ALL;
        }
        else if(publicFLG.equals("public")&&readFLG.equals("read")){
            folderPath = RMIServer.FILE_PUBLIC_READ;
        }
        File file = new File(folderPath+fileName);

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
        sSocket.close();

        System.out.println("Download success! "+userName+": "+fileName);
                 
        } catch (IOException ex) {
        Logger.getLogger(ThreadTcpUpload.class.getName()).log(Level.SEVERE, null, ex);
    }
}
    
}
