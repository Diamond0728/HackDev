package com.group2.project.shiro;
import java.io.Serializable;

public class AppShiroUser implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 8494048657048720145L;

    private String id;	// 用户ID

    private String accessToken;	// 用户oauth授权accessToken

    private String ipAddress;//用户登录的机器IP

    public AppShiroUser(String id, String accessToken) {
        super();
        this.id = id;
        this.accessToken = accessToken;
    }

    public String getId() {
        return id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * @return the ipAddress
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * @param ipAddress the ipAddress to set
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * 本函数输出将作为默认的<shiro:principal/>输出.
     */
    @Override
    public String toString() {
        return id;
    }
}