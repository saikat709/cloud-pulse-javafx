package com.saikat.cloudpulse.listeners;

public interface CompleteOrFailureListener {
    public void onComplete();
    public void onFailure();
}
