package com.saikat.cloudpulse.listeners;

public interface ApiCallListener<T>{
    public void onApiCallSuccess(T result);
    public void onApiCallFailure(String errorMessage);
}
