package com.group2.project.entity;

public class BorrowReturn {
    Integer br_id;
    Integer u_id;
    Integer b_id;
    String btime;
    String rtime;

    public Integer getBr_id() {
        return br_id;
    }

    public void setBr_id(Integer br_id) {
        this.br_id = br_id;
    }

    public Integer getU_id() {
        return u_id;
    }

    public void setU_id(Integer u_id) {
        this.u_id = u_id;
    }

    public Integer getB_id() {
        return b_id;
    }

    public void setB_id(Integer b_id) {
        this.b_id = b_id;
    }

    public String getBtime() {
        return btime;
    }

    public void setBtime(String btime) {
        this.btime = btime;
    }

    public String getRtime() {
        return rtime;
    }

    public void setRtime(String rtime) {
        this.rtime = rtime;
    }
}
