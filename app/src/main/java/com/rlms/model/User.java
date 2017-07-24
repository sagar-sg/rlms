package com.rlms.model;

public class User {

//    {"userName":null,"password":null,"userRoleId":1,"userId":1,"companyId":1}

    private String userName = "";
    private String password = "";
    private int userId = 0;
    private int companyId = 0;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", userId=" + userId +
                ", companyId=" + companyId +
                '}';
    }
}
