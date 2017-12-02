package rmi.server.startup;


import rmi.server.controller.SystemInitial;
import java.io.*;
import java.text.DecimalFormat;
public class RMIServer { 
public static String SERVER_IP;
public static String FILE_STORAGE_PATH = "/Users/harry/Desktop/UserFile/";
public static String FILE_PUBLIC_ALL = "/Users/harry/Desktop/UserFile/Public/AllPermission/";
public static String FILE_PUBLIC_READ = "/Users/harry/Desktop/UserFile/Public/ReadOnly/";
public static void main(String[] args) throws Exception{ 
    SystemInitial.interfaceInitial();
    
} 
public static String getCTX(String originalCTX,String firstSplit,String secondSplit){
        String resultCTX = originalCTX.substring(originalCTX.lastIndexOf(firstSplit), 
        originalCTX.lastIndexOf(secondSplit));
        resultCTX = resultCTX.substring(1,resultCTX.length());
        return resultCTX;
    }

public static String getFileSize(String filePath){
    DecimalFormat dFormat = new DecimalFormat("#0.00");
    File file = new File(filePath);
    double fileSize = file.length();
    String temp = dFormat.format(fileSize/1024/1024);
    String returnSize = "("+temp+"MB)";
    return returnSize;
}

public static String getOldFileName(String fileName,String fileBase){
        int i = fileBase.indexOf(fileName);
        i = i - 2;
        int k = i;
        i = i - 1;
        int j = 0;
        while (i > 0){
            if (fileBase.charAt(i) == '"')
            {   
                j = i;
                break;}
            i--;
        }
        String olduserName = fileBase.substring(j+1, k);
        return olduserName;
}

} 