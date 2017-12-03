package rmi.server.startup;


import rmi.server.controller.SystemInitial;
import java.io.*;
import java.text.DecimalFormat;
public class RMIServer { 
public static String SERVER_IP;
public static String FILE_STORAGE_PATH = "/Users/huangshimin/Desktop/UserFile/";//All the File will be stored in UserFile document
public static String FILE_PUBLIC_ALL = "/Users/huangshimin/Desktop/UserFile/Public/AllPermission/";//The file with all authentication (both read and write) will be stored in AllPermission document
public static String FILE_PUBLIC_READ = "/Users/huangshimin/Desktop/UserFile/Public/ReadOnly/";//The read only file will be stored in ReadOnly document
public static void main(String[] args) throws Exception{ 
    SystemInitial.interfaceInitial();
    
} 
public static String getCTX(String originalCTX,String firstSplit,String secondSplit){
    //this function is to extract the context from the encapsulated datagram
        String resultCTX = originalCTX.substring(originalCTX.lastIndexOf(firstSplit), 
        originalCTX.lastIndexOf(secondSplit));
        resultCTX = resultCTX.substring(1,resultCTX.length());
        return resultCTX;
    }

public static String getFileSize(String filePath){
    //this function is to calculate file size and transform the format of the file size
    DecimalFormat dFormat = new DecimalFormat("#0.00");
    File file = new File(filePath);
    double fileSize = file.length();
    String temp = dFormat.format(fileSize/1024/1024);//transform the format of file size
    String returnSize = "("+temp+"MB)";//the file size will be shown in format MB
    return returnSize;
}

public static String getOldFileName(String fileName,String fileBase){
    //this function is to get the original name of the file
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