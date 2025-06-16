package com.saikat.cloudpulse.models;

import java.util.List;

public class ForecastModel {
    private String cod;
    private int cnt;
    private String message;
    private List<Forecast> list;
    private ForecastCityInfo city;

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Forecast> getList() {
        return list;
    }

    public void setList(List<Forecast> list) {
        this.list = list;
    }

    public ForecastCityInfo getCity() {
        return city;
    }

    public void setCity(ForecastCityInfo city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "ForecastModel{" +
                "cod='" + cod + '\'' +
                ", cnt=" + cnt +
                ", message='" + message + '\'' +
                ", list=" + list +
                ", city=" + city +
                '}';
    }
}
