package com.group.project.common;

public class ResponseDomain<T> {
    boolean success = true;
    T data;
    String message;

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

    public ResponseDomain<T> fail(String message) {
        success = false;
        this.message=message;
        return this;
    }
    public ResponseDomain<T> success(T data) {
        this.data=data;
        return this;
    }

    public ResponseDomain<T> fail() {
        return fail(null);
    }
}
