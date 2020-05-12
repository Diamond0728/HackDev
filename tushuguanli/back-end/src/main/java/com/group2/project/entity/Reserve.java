package com.group2.project.entity;

public class Reserve {
    Integer u_id;
    String isbn;
    String reservetime;
    String deadline;

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public Integer getU_id() {
        return u_id;
    }

    public void setU_id(Integer u_id) {
        this.u_id = u_id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getReservetime() {
        return reservetime;
    }

    public void setReservetime(String reservetime) {
        this.reservetime = reservetime;
    }

}
