package rmi.server.model;

public class UserInfo {
    private static String userName;
    private static String userPassword;
    private static String userFileName;

    public UserInfo(String userName, String userPassword, String userFileName) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userFileName = userFileName;
    }


    public static String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public static String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public static String getUserFileName() {
        return userFileName;
    }

    public void setUserFileName(String userFileName) {
        this.userFileName = userFileName;
    }
}