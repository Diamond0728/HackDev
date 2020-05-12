package com.group2.project.entity;

public class Role {
    Integer r_id;
    String rolename;
    String mask;

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }


    public Integer getR_id() {
        return r_id;
    }

    public void setR_id(Integer r_id) {
        this.r_id = r_id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }
}
