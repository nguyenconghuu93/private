package com.vicloud.vn.models;

/**
 * Created by huunc on 7/22/16.
 */
public class Response {
    int httpCode;
    String data;

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
