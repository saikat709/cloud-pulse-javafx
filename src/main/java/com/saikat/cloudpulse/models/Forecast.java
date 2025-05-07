package com.saikat.cloudpulse.models;

import java.util.List;

public class Forecast {
    private Integer dt;
    private WeatherMain main;
    private List<Weather> weather;
    private Clouds clouds;
    private Wind wind;
    private String dt_txt;

    public Integer getDt() {
        return dt;
    }
    public void setDt(Integer dt) {
        this.dt = dt;
    }
    public WeatherMain getMain() {
        return main;
    }
    public void setMain(WeatherMain main) {
        this.main = main;
    }
    public List<Weather> getWeather() {
        return weather;
    }
    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }
    public Clouds getClouds() {
        return clouds;
    }
    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }
    public Wind getWind() {
        return wind;
    }
    public void setWind(Wind wind) {
        this.wind = wind;
    }
    public String getDt_txt() {
        return dt_txt;
    }
    public void setDt_txt(String dt_txt) {
        this.dt_txt = dt_txt;
    }


    @Override
    public String toString() {
        return "Forecast{" +
                "dt=" + dt +
                ", main=" + main +
                ", weather=" + weather +
                ", clouds=" + clouds +
                ", wind=" + wind +
                ", dt_txt='" + dt_txt + '\'' +
                '}';
    }
}
