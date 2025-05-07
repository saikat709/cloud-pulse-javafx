package com.saikat.cloudpulse.api;

import com.google.gson.Gson;
import com.saikat.cloudpulse.listeners.ApiResponseListener;
import javafx.concurrent.Task;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RetrieveFromApi {
    public void callAPI(String url, ApiResponseListener apiResponseListener) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        Gson gson = new Gson();

        Task<String> apiTask = new Task<>() {
            @Override
            protected String call() throws Exception {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if ( response.statusCode() != 200 ) {
                    apiResponseListener.onFailure("API call failed with status code: " + response.statusCode());
                }
                return response.body();
            }
        };

        apiTask.setOnSucceeded(e -> {
            try {
                String data = apiTask.getValue();
                apiResponseListener.onSuccess(data);
            } catch (Exception ex) {
                apiResponseListener.onFailure(ex.getMessage());
            }
        });

        apiTask.setOnFailed(e -> {
            apiResponseListener.onFailure(apiTask.getException().getMessage());
        });

        new Thread(apiTask).start();
    }
}
