package com.saikat.cloudpulse.storage;

public class AppStorage {
    private String FILE_PATH = "~/.CloudPulse";
    private String FILE_NAME = "cloudpulse.txt";

    private static AppStorage appStorage;

    private AppStorage() {}

    public static AppStorage getInstance() {
        if ( appStorage == null ) {
            appStorage = new AppStorage();
        }
        return appStorage;
    }

    public static void saveToDevice(String data) {

    }

    public static String loadFromDevice() {
        return "";
    }

    public String getFILE_PATH() {
        return FILE_PATH;
    }

    public void setFILE_PATH(String FILE_PATH) {
        this.FILE_PATH = FILE_PATH;
    }

    public String getFILE_NAME() {
        return FILE_NAME;
    }

    public void setFILE_NAME(String FILE_NAME) {
        this.FILE_NAME = FILE_NAME;
    }
}
