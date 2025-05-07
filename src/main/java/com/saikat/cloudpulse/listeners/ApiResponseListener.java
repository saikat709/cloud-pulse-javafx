package com.saikat.cloudpulse.listeners;

public interface ApiResponseListener {
    public void onFailure(String message);
    public void onSuccess(String message);
}
