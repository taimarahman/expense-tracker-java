package com.project.expenseTracker.dto.response;

public interface ApiResponse {
    boolean isSuccess();
    String getMessage();
    Object getData();
    int getStatusCode();
}
