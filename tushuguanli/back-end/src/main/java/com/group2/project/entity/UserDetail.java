package com.group2.project.entity;

import java.util.Map;

public class UserDetail {
    Integer u_id;
    String username;
    Map<String,Boolean> roles;
    Map<String,Boolean> permissions;

    public Integer getU_id() {
        return u_id;
    }

    public void setU_id(Integer u_id) {
        this.u_id = u_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Map<String, Boolean> getRoles() {
        return roles;
    }

    public void setRoles(Map<String, Boolean> roles) {
        this.roles = roles;
    }

    public Map<String, Boolean> getPermissions() {
        return permissions;
    }

    public void setPermissions(Map<String, Boolean> permissions) {
        this.permissions = permissions;
    }


}
