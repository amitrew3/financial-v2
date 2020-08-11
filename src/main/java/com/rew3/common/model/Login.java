package com.rew3.common.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

public class Login {


    private String username;

    private String password;

    private final String GRANT_TYPE = "";

    private final String CLIENT_ID = "";

    private final String CLIENT_SECRET = "";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGRANT_TYPE() {
        return GRANT_TYPE;
    }

    public String getCLIENT_ID() {
        return CLIENT_ID;
    }

    public String getCLIENT_SECRET() {
        return CLIENT_SECRET;
    }
}
