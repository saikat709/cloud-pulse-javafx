package com.saikat.cloudpulse.manager;

import com.saikat.cloudpulse.models.City;
import com.saikat.cloudpulse.utils.FuzzySearchUtil;
import java.util.ArrayList;
import java.util.List;

public class CityListManager {

    private static CityListManager ins;
    private final List<City> allCities;
    private boolean isCitySet = false;

    private CityListManager(){
        allCities = new ArrayList<>();
    }

    public static CityListManager getInstance(){
        if ( ins == null ){
            ins = new CityListManager();
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
        System.out.println("CHECK: " + isCitySet);
        if ( !isCitySet ) return new ArrayList<>();
        return allCities;
    }

    public void setCities(List<City> cities) {
        this.allCities.clear();
        for( City city : cities){
            // TODO: not checking  !hasCity(city) since the list is huge and it takes a lot of time
            if ( city.getName().length() > 2  ) allCities.add(city);
        }
        isCitySet = true;
    }

    private boolean hasCity(City city){
        for( City existingCity: this.allCities ){
            if ( existingCity.getName().equals(city.getName()) ) return true;
        }
        return false;
    }

}
