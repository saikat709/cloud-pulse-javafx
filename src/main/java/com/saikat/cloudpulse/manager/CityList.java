package com.saikat.cloudpulse.manager;

import com.saikat.cloudpulse.models.City;
import com.saikat.cloudpulse.utils.FuzzySearchUtil;
import java.util.ArrayList;
import java.util.List;

public class CityList {

    private static CityList ins;
    private final List<City> allCities;

    private CityList(){
        allCities = new ArrayList<>();
    }

    public static CityList getInstance(){
        if ( ins == null ){
            ins = new CityList();
        }
        return ins;
    }

    public List<City> getFuzzySearchedCityNames(String input){
        if ( allCities.isEmpty() ) {
            return new ArrayList<>();
        }

        return FuzzySearchUtil.getFuzzyCityNames(allCities, input);
    }

    public List<City> getCities() {
        return allCities;
    }

    public void setCities(List<City> cities) {
        this.allCities.clear();
        for( City city : cities){
            if ( !hasCity(city) && city.getName().length() > 2  ) allCities.add(city);
        }
    }

    private boolean hasCity(City city){
        for( City existingCity: this.allCities ){
            if ( existingCity.getName().equals(city.getName()) ) return true;
        }
        return false;
    }

}
