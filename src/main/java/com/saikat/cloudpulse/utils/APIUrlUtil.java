package com.saikat.cloudpulse.utils;

import io.github.cdimascio.dotenv.Dotenv;

import static com.saikat.cloudpulse.constants.ConstValues.BASE_URL;

public class APIUrlUtil {

    public static String getApiKey(){
        Dotenv dotenv = Dotenv.load();
        return dotenv.get("API_KEY");
    }

    public static String foreCastUrlFromCityName(String cityName, String countryCode, String apiKey){
        return BASE_URL + "forecast?q="
                + cityName + "," + countryCode
                + "&format=json&APPID=" + apiKey;
    }

    public static String weatherUrlFromCityName(String cityName, String countryCode, String apiKey){
        return BASE_URL + "weather?q="
                + cityName + "," + countryCode
                + "&format=json&APPID=" + apiKey;
    }

    public static String foreCastUrlFromLatLong(Double latitude, Double longitude, String apiKey){
        return BASE_URL + "forecast?lat="
                + latitude + "&lon=" + longitude
                + "&format=json&APPID=" + apiKey;
    }

    public static String weatherUrlFromLatLong(Double latitude, Double longitude, String apiKey){
        return BASE_URL + "weather?lat="
                + latitude + "&lon=" + longitude
                + "&format=json&APPID=" + apiKey;
    }
}
