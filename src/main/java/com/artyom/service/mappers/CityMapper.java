package com.artyom.service.mappers;

import com.artyom.controller.advice.exceptions.CityNotFoundException;
import com.artyom.dto.CityDto;
import com.artyom.entity.City;
import com.artyom.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CityMapper {

    private final CityRepository cityRepository;

    @Autowired
    public CityMapper(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public CityDto cityToDto(City city){
        return new CityDto(city.getId(), city.getName());
    }

    public List<City> namesToCities(List<String> names) throws CityNotFoundException{
        List<City> cities = new ArrayList<>();
        for(String name:names){
            cities.add(cityRepository.findCityByName(name).orElseThrow(() -> new CityNotFoundException("City with name " + name + " is not found")));
        }
        return cities;
    }
}
