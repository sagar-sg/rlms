package com.rlms.model;

/**
 * Created by Swapnil on 3/17/2017.
 */

public class UserRole {

    String userRoleId = "";

    public UserRole(String userRoleId) {
        this.userRoleId = userRoleId;
    }

    public String getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(String userRoleId) {
        this.userRoleId = userRoleId;
    }
}
