package com.example.demo.apiresponsemodel;

import java.util.Vector;

public class APIResponseModel {
	private String status = "Failed";
    private Vector<?> data;
    private String message = "Something went wrong";
    private int count = 0;

    public APIResponseModel(String status, String message, Vector<?> data, int count) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.count = count;
    }

    public APIResponseModel() {
        super();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Vector<?> getData() {
        return data;
    }

    public void setData(Vector<?> data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
