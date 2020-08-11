package com.rew3.common.application;

public class AuthenticatedUser {
    private String id;
    private String firstName;
    private String lastName;


    public AuthenticatedUser(String id, String firstName, String lastName, String memberId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;

    }

    public AuthenticatedUser(String id, String userFirstName, String userLastName) {
        this.id=id;
        this.firstName=userFirstName;
        this.lastName=userLastName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
