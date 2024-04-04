package com.thethelafaltein.chatroom.model;

public class LoginDetails {
    String username;
    String password;
    Boolean isAUser;

    public LoginDetails(String username,String password,Boolean isAUser){
        this.isAUser = isAUser;
        this.username = username;
        this.password = password;
    }

    // Getter for username
    public String getUsername() {
        return username;
    }

    // Setter for username
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter for password
    public String getPassword() {
        return password;
    }

    // Setter for password
    public void setPassword(String password) {
        this.password = password;
    }

    // Getter for isAUser
    public Boolean getIsAUser() {
        return isAUser;
    }

    // Setter for isAUser
    public void setIsAUser(Boolean isAUser) {
        this.isAUser = isAUser;
    }
}
