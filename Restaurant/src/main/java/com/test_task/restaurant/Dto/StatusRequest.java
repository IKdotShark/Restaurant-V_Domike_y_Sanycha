package com.test_task.restaurant.Dto;

public class StatusRequest {
    private String status;

    public StatusRequest() {}

    public StatusRequest(String request) {
        this.status = request;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
