/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi.server.model;

/**
 *
 * @author harry
 */
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