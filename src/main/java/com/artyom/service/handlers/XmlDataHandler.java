package com.artyom.service.handlers;

import com.artyom.dto.upload.UploadCityDto;
import com.artyom.dto.upload.UploadData;
import com.artyom.dto.upload.UploadDistanceDto;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class XmlDataHandler extends DefaultHandler {
    private static final String CITIES = "cities";
    private static final String CITY = "city";
    private static final String DISTANCES = "distances";
    private static final String DISTANCE = "distance";
    private static final String NAME = "name";
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";
    private static final String FROM_CITY_NAME = "fromCityName";
    private static final String TO_CITY_NAME = "toCityName";
    private static final String CITY_DISTANCE = "cityDistance";

    private UploadData uploadData;
    private StringBuilder elementValue;

    @Override
    public void characters(char[] ch, int start, int length) {
        if (elementValue == null){
            elementValue = new StringBuilder();
        } else{
            elementValue.append(ch, start, length);
        }
    }

    @Override
    public void startDocument() {
        uploadData = new UploadData();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        switch (qName){
            case CITIES:
                uploadData.setCityDtos(new ArrayList<>());
                break;
            case CITY:
                List<UploadCityDto> cityDtos = uploadData.getCityDtos();
                cityDtos.add(new UploadCityDto());
                uploadData.setCityDtos(cityDtos);
                break;
            case DISTANCES:
                uploadData.setDistanceDtos(new ArrayList<>());
                break;
            case DISTANCE:
                List<UploadDistanceDto> distanceDtos = uploadData.getDistanceDtos();
                distanceDtos.add(new UploadDistanceDto());
                uploadData.setDistanceDtos(distanceDtos);
                break;
            case NAME:
            case TO_CITY_NAME:
            case LATITUDE:
            case LONGITUDE:
            case FROM_CITY_NAME:
            case CITY_DISTANCE:
                elementValue = new StringBuilder();
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        switch (qName){
            case NAME:
                latestCity().setName(elementValue.toString());
                break;
            case LATITUDE:
                latestCity().setLatitude(new BigDecimal(elementValue.toString()));
                break;
            case LONGITUDE:
                latestCity().setLongitude(new BigDecimal(elementValue.toString()));
                break;
            case FROM_CITY_NAME:
                latestDistance().setFromCityName(elementValue.toString());
                break;
            case TO_CITY_NAME:
                latestDistance().setToCityName(elementValue.toString());
                break;
            case CITY_DISTANCE:
                latestDistance().setDistance(Double.valueOf(elementValue.toString()));
                break;
        }
    }

    public UploadCityDto latestCity(){
        List<UploadCityDto> dtos = uploadData.getCityDtos();
        int latest = dtos.size() - 1;
        return dtos.get(latest);
    }

    public UploadDistanceDto latestDistance(){
        List<UploadDistanceDto> dtos = uploadData.getDistanceDtos();
        int latest = dtos.size() - 1;
        return dtos.get(latest);
    }

    public UploadData getUploadData() {
        return uploadData;
    }
}
