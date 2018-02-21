package com.sbinventory.model;
 
public class AppUser {
 
    private int userId;
    private int userCode;
    private String userName;
    private String encrytedPassword;
 
    public AppUser() {
 
    }
 
    public AppUser(int userId, int userCode, String userName, String encrytedPassword) {
        this.userId = userId;
        this.userCode = userCode;
        this.userName = userName;
        this.encrytedPassword = encrytedPassword;
    }
 
    public int getUserId() {
        return userId;
    }
 
    public void setUserId(int userId) {
        this.userId = userId;
    }
 
    public int getUserCode() {
		return userCode;
	}

	public void setUserCode(int userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
        return userName;
    }
 
    public void setUserName(String userName) {
        this.userName = userName;
    }
 
    public String getEncrytedPassword() {
        return encrytedPassword;
    }
 
    public void setEncrytedPassword(String encrytedPassword) {
        this.encrytedPassword = encrytedPassword;
    }
 
    @Override
    public String toString() {
        return this.userName + "/" + this.encrytedPassword;
    }
 
    
}