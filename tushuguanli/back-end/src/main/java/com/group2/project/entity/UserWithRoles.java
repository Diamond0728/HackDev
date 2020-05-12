package com.group2.project.entity;

import java.util.List;
import java.util.Map;

public class UserWithRoles {
    Integer u_id;
    String username;
    String name;
    String phone;
    String email;
    List roles;

    public List getRoles() {
        return roles;
    }

    public void setRoles(List roles) {
        this.roles = roles;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
