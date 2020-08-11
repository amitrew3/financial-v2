package com.rew3.common.interceptor;


public class ACL {

    String creatorId;

    String subscriberId;

    String workspaceId;

    String projectId;

    Boolean gr;     // global write

    Boolean gw;     // global read

    Boolean gd;     // global delete

    Permission r;

    Permission w;

    Permission d;

    Permission mr;

    Permission mw;

    Permission md;

    public String getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(String workspaceId) {
        this.workspaceId = workspaceId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }


    public Boolean getGr() {
        return gr;
    }

    public void setGr(Boolean gr) {
        this.gr = gr;
    }

    public Boolean getGw() {
        return gw;
    }

    public void setGw(Boolean gw) {
        this.gw = gw;
    }

    public Permission getR() {
        return r;
    }

    public void setR(Permission r) {
        this.r = r;
    }

    public Permission getW() {
        return w;
    }

    public void setW(Permission w) {
        this.w = w;
    }

    public Permission getD() {
        return d;
    }

    public void setD(Permission d) {
        this.d = d;
    }

    public Permission getMr() {
        return mr;
    }

    public void setMr(Permission mr) {
        this.mr = mr;
    }

    public Permission getMw() {
        return mw;
    }

    public void setMw(Permission mw) {
        this.mw = mw;
    }

    public Permission getMd() {
        return md;
    }

    public void setMd(Permission md) {
        this.md = md;
    }

    public Boolean getGd() {
        return gd;
    }

    public void setGd(Boolean gd) {
        this.gd = gd;
    }


}