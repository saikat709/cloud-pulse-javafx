package com.saikat.cloudpulse.models;

public class City {
    Integer id;
    String name;
    String state;
    String country;
    Coord  coord;

    public String getName() {
        return name;
    }

    public Double getLatitude(){
        return this.coord.getLat();
    }

    public Double getLongitude(){
        return this.coord.getLon();
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", coord=" + coord +
                '}';
    }
}
