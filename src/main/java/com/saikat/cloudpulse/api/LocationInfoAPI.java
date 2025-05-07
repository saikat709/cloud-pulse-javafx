package com.saikat.cloudpulse.api;

import com.google.gson.Gson;
import com.saikat.cloudpulse.listeners.ApiCallListener;
import com.saikat.cloudpulse.listeners.ApiResponseListener;
import com.saikat.cloudpulse.models.LocationInfoModel;

public class LocationInfoAPI extends RetrieveFromApi{
    private String API_URL = "https://ipinfo.io/json";
    private final Gson gson = new Gson();

    public void getLocationInfo(ApiCallListener<LocationInfoModel> listener) {

        this.callAPI(API_URL, new ApiResponseListener() {
            @Override
            public void onFailure(String message) {
                listener.onApiCallFailure(message);
            }

            @Override
            public void onSuccess(String message) {
                try {
                    LocationInfoModel model = gson.fromJson(message, LocationInfoModel.class);
                    listener.onApiCallSuccess(model);
                } catch (Exception ex) {
                    listener.onApiCallFailure(ex.getLocalizedMessage());
                }
            }
        });
    }

    public String getAPI_URL() {
        return API_URL;
    }

    public void setAPI_URL(String API_URL) {
        this.API_URL = API_URL;
    }
}
