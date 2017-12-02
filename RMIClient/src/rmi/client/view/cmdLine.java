/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi.client.view;

import java.io.File;
import java.util.Scanner;
import rmi.client.startup.RMIClient;

/**
 *
 * @author harry
 */
public class cmdLine {

    
    public static void println(String result){
        System.out.println(result);
    }
    
    public static void userInitialCMD(){
        System.out.println("Welcome to File Storage System");
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter server IP address:");
        RMIClient.SERVER_IP = sc.nextLine();
    }
    
    public static String firstJudgeCMD(){
        Scanner sc = new Scanner(System.in);
        System.out.println("\nPlease enter the service number: \n>> Press \"1\" login; \n>> "
                    + "Press \"2\" Register; \n>> Press \"3\" Quit;");
        String userAttempt = sc.nextLine();
        return userAttempt;
    }
    
    public static String userLoginCMD(){
        Scanner sc = new Scanner(System.in);
        System.out.println(">> Please input your username:");
        RMIClient.USER_NAME_TEMP = sc.nextLine();
        System.out.println(">> Please input your Password:");
        String userPassWord = sc.nextLine();
        String sendCTX = "(1@"+RMIClient.USER_NAME_TEMP+"#"+userPassWord+")";
        return sendCTX;
    }
    
    public static String userRegisterCMD(){
        Scanner sc = new Scanner(System.in);
        String userPassWord = null;
        System.out.println("\nBegin register:");
        System.out.println(">> Please input your username:");
        RMIClient.USER_NAME_TEMP = sc.nextLine();
        while(true){
            System.out.println(">> Please input your Password:");
            String tempPassWord1 = sc.nextLine();
            System.out.println(">> Please repeat your Password:");
            String tempPassWord2 = sc.nextLine();
            if (tempPassWord1.equals(tempPassWord2))
            {
                userPassWord = tempPassWord1;
                break;
            }
            System.out.println("Password Verify Failed\n");
        }
        return "(2@"+RMIClient.USER_NAME_TEMP+"#"+userPassWord+")";
    }
    
    
    public static String operationJudgeCMD(){
        Scanner sc = new Scanner(System.in);
        System.out.println("\nWelcome "+RMIClient.USER_NAME+"!"+" Please enter the service number!");
        System.out.println(">> Press \"0\" Show your file list; \n>> Press \"1\" Upload; \n>> "
                    + "Press \"2\" Download; \n>> Press \"3\" Update; \n>> Press \"4\" Delete; "
                + "\n>> Press \"5\" Unregister; \n>> Press \"6\" Log out;");
        String userAttempt = sc.nextLine();
        return userAttempt;
    }
    
    public static String quitFLG(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter \"Quit\" to quit!");
        String quitFLG = sc.nextLine();
        return quitFLG;
    }
    
    public static String getFileName(){
        Scanner sc = new Scanner(System.in);
        String fileName = sc.nextLine();
        return fileName;
    }
    
    public static String getFilePath(){
        Scanner sc = new Scanner(System.in);
        String filePath = sc.nextLine();
        return filePath;
    }
    
    public static String userUnregisterCMD(){
        Scanner sc = new Scanner(System.in);
        System.out.println("\nBegin UnRegister:");
        System.out.println("If you remove your account, all the files will be deleted. Do you want to continue(Y/N)");
        String userConfirm = sc.nextLine();
        return userConfirm;
    }
    
    public static String unregisterConf(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter \"Y\" or \"N\" !");
        String userConfirm = sc.nextLine();
        return userConfirm;
    }
    
    public static String fileFeatureSele(){
        Scanner sc = new Scanner(System.in);
        System.out.println(">> 1: Public(Read only) \n>> 2: Public(All permission) \n>> 3: Private");
        String publicFLG =sc.nextLine();
        return publicFLG;
    }
    
    public static String enterStoragePath(){
        System.out.println("Please input your storage path:");
        Scanner sc = new Scanner(System.in);
        String userFilePath = null;
        while (true){

            userFilePath = sc.nextLine();

            if(new File(userFilePath).exists()){
                break;
            }
            else{
                System.out.println("Path doesn't exist! Please enter again!"); 
            }
        }
        return userFilePath;
    }
    
}
