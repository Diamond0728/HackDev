package com.group.project.common;

public class SocketResponseDomain<T> {
    boolean success = true;
    String type = new String();
    T data;
    String message;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public SocketResponseDomain<T> fail(String message) {
        success = false;
        this.message=message;
        return this;
    }
    public SocketResponseDomain<T> success(T data) {
        this.data=data;
        return this;
    }

    public SocketResponseDomain<T> fail() {
        return fail(null);
    }
}
