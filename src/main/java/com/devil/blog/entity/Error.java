package com.devil.blog.entity;

public class Error {
    private int code;
    private String message;
    private String detail;
    
    public Error() {
    }
    public Error(int code, String message, String detail) {
        this.code = code;
        this.message = message;
        this.detail = detail;
    }

    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getDetail() {
        return detail;
    }
    public void setDetail(String detail) {
        this.detail = detail;
    }
    @Override
    public String toString() {
        return "Error [code=" + code + ", message=" + message + ", detail=" + detail + "]";
    }
}
