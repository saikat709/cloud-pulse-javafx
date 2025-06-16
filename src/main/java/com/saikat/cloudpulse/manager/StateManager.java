package com.saikat.cloudpulse.manager;

public class StateManager {
    public static StateManager instance;

    private String  userName;
    private boolean canGoBack;

    private StateManager() {
        super();
        canGoBack = false;
    }

    public static StateManager getInstance() {
        if (instance == null) {
            instance = new StateManager();
        }
        return instance;
    }

    public boolean isCanGoBack() {
        return canGoBack;
    }

    public void setCanGoBack(boolean canGoBack) {
        this.canGoBack = canGoBack;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
