package com.rew3.common.interceptor;


import java.util.List;

public class Permission {
    Permission() {

    }

    public Permission(List<String> userIds, List<String> grpIds) {
        this.userIds = userIds;
        this.grpIds = grpIds;
    }

    private List<String> userIds;

    private List<String> grpIds;


    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public List<String> getGrpIds() {
        return grpIds;
    }

    public void setGrpIds(List<String> grpIds) {
        this.grpIds = grpIds;
    }
}