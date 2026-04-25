package com.clickmart.backend.dto;

public class ApiResponse<T> {

    private boolean success;
    private T data;
    private Object error;

    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.data = success ? data : null;
        this.error = success ? null : message;
    }

    public ApiResponse(boolean success, String message) {
        this(success, message, null);
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

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }
}
