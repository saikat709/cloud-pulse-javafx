package com.saikat.cloudpulse.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class AppStorage {
    private static String FILE_PATH = System.getProperty("user.home") + "/.CloudPulse";
    private static final String FILE_NAME = "cloudpulse.txt";

    private SavableInfo savableInfo;
    private final File file;
    private final Gson gson;

    private static volatile AppStorage instance;

    private AppStorage() {
        this.file = initStorageFile();
        this.gson = new GsonBuilder()
                .serializeNulls()
                .create();
        this.savableInfo = new SavableInfo();
    }

    public static synchronized AppStorage getInstance() {
        if (instance == null) {
            instance = new AppStorage();
        }
        return instance;
    }

    private File initStorageFile() {
        File directory = new File(FILE_PATH);

        if (!directory.exists() && !directory.mkdirs()) {
            System.err.println("ERROR: Could not create directory: " + FILE_PATH);
            FILE_PATH = System.getProperty("java.io.tmpdir") + "/CloudPulse";
            directory = new File(FILE_PATH);

            if (!directory.mkdirs()) {
                System.err.println("ERROR: Could not create fallback directory: " + FILE_PATH);
            } else {
                System.out.println("Using fallback directory: " + FILE_PATH);
            }
        }

        File storageFile = new File(FILE_PATH, FILE_NAME);
        if (!storageFile.exists()) {
            try {
                if (storageFile.createNewFile()) {
                    System.out.println("Created file: " + storageFile.getAbsolutePath());
                } else {
                    throw new IOException("Could not create file: " + storageFile.getAbsolutePath());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Using existing file: " + storageFile.getAbsolutePath());
        }
        return storageFile;
    }

    private void saveToStorage() throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(savableInfo, writer);
        }
    }

    public void loadFromStorage() {
        try (FileReader reader = new FileReader(file)) {
            StringBuilder content = new StringBuilder();
            int ch;
            while ((ch = reader.read()) != -1) {
                content.append((char) ch);
            }

            if (!content.toString().trim().isEmpty()) {
                try {
                    savableInfo = gson.fromJson(content.toString(), SavableInfo.class);
                    if (savableInfo == null) {
                        savableInfo = new SavableInfo();
                    }
                } catch (Exception e) {
                    System.err.println("Error parsing JSON: " + e.getMessage());
                    savableInfo = new SavableInfo();
                }
            } else {
                savableInfo = new SavableInfo();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read from storage", e);
        }
    }

    public String getSavedName() {
        if (!file.exists()) {
            System.out.println("File does not exist: " + file.getAbsolutePath());
            return null;
        }
        loadFromStorage();
        return (savableInfo != null) ? savableInfo.getUserName() : null;
    }

    public void saveUserName(String userName) {
        if (savableInfo == null) {
            savableInfo = new SavableInfo();
        }
        savableInfo.setUserName(userName);

        File directory = new File(FILE_PATH);
        if (!directory.exists() && !directory.mkdirs()) {
            System.err.println("ERROR: Could not create directory: " + FILE_PATH);
            return;
        }

        try {
            saveToStorage();
            System.out.println("Successfully saved user name: " + userName);
        } catch (IOException e) {
            System.err.println("Could not save to file: " + e.getMessage());
        }
    }

    public String getFilePath() {
        return FILE_PATH;
    }

    public void setFilePath(String path) {
        FILE_PATH = path;
    }

    public String getFileName() {
        return FILE_NAME;
    }
}
