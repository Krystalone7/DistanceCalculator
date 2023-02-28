package com.artyom.service;

import com.artyom.controller.advice.exceptions.CityNotFoundException;
import com.artyom.controller.advice.exceptions.DistanceNotFoundException;
import com.artyom.controller.advice.exceptions.UnknownOperationException;
import com.artyom.dto.CityDto;
import com.artyom.dto.DistanceDto;
import com.artyom.dto.calculation.CalcDto;
import com.artyom.dto.calculation.CalcInputDto;
import com.artyom.dto.upload.UploadCityDto;
import com.artyom.dto.upload.UploadData;
import com.artyom.dto.upload.UploadDistanceDto;
import com.artyom.entity.City;
import com.artyom.entity.Distance;
import com.artyom.repository.CityRepository;
import com.artyom.repository.DistanceRepository;
import com.artyom.service.handlers.XmlDataHandler;
import com.artyom.service.mappers.CityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityService {
    private final CityRepository cityRepository;
    private final DistanceRepository distanceRepository;

    private final CityMapper cityMapper;

    @Autowired
    public CityService(CityRepository cityRepository, DistanceRepository distanceRepository, CityMapper cityMapper) {
        this.cityRepository = cityRepository;
        this.distanceRepository = distanceRepository;
        this.cityMapper = cityMapper;
    }

    public List<CityDto> getAll() {
        return cityRepository.findAll().stream()
                .map(cityMapper::cityToDto)
                .collect(Collectors.toList());
    }

    public Double findDistance(City fromCity, City toCity){
        Distance distance = distanceRepository.findDistanceByFromCityNameAndToCityName(fromCity.getName(), toCity.getName()).orElse(null);
        if (distance == null){
            distance = distanceRepository.findDistanceByFromCityNameAndToCityName(toCity.getName(), fromCity.getName()).orElse(null);
            if (distance == null){
                throw new DistanceNotFoundException("Distance between cities " + fromCity.getName() + " and " + toCity.getName() + " is not found");
            }
        }
        return distance.getDistance();
    }

    public CalcDto calculate(CalcInputDto calcInputDto) throws UnknownOperationException, CityNotFoundException {
        String type = calcInputDto.getCalcType();
        List<City> fromCities = cityMapper.namesToCities(calcInputDto.getFromCityNames());
        List<City> toCities = cityMapper.namesToCities(calcInputDto.getToCityNames());
        List<DistanceDto> distances = new ArrayList<>();
        for (City fromCity : fromCities) {
            for (City toCity : toCities) {
                if (!fromCity.equals(toCity)) {
                    DistanceDto dto;
                    switch (type) {
                        case "Crowflight":
                            dto = new DistanceDto(
                                    fromCity.getName(),
                                    toCity.getName(),
                                    crowFlightCalc(fromCity, toCity),
                                    (double) -1
                            );
                            break;
                        case "Distance Matrix":
                            dto = new DistanceDto(
                                    fromCity.getName(),
                                    toCity.getName(),
                                    (double) -1,
                                    findDistance(fromCity, toCity)
                            );
                            break;
                        case "All":
                            dto = new DistanceDto(
                                    fromCity.getName(),
                                    toCity.getName(),
                                    crowFlightCalc(fromCity, toCity),
                                    findDistance(fromCity, toCity)
                            );
                            break;
                        default:
                            throw new UnknownOperationException("Wrong operation");
                    }
                    distances.add(dto);
                }
            }
        }
        return new CalcDto(distances);
    }

    public Double crowFlightCalc(City city1, City city2) {
        BigDecimal lat1 = city1.getLatitude();
        BigDecimal lon1 = city1.getLongitude();
        BigDecimal lat2 = city2.getLatitude();
        BigDecimal lon2 = city2.getLongitude();

        double dLon = Math.toRadians(lon2.subtract(lon1).doubleValue());
        double dLat = Math.toRadians(lat2.subtract(lat1).doubleValue());
        double lt1 = Math.toRadians(lat1.doubleValue());
        double lt2 = Math.toRadians(lat2.doubleValue());
        double clt1 = Math.cos(lt1);
        double clt2 = Math.cos(lt2);
        double a = Math.pow(Math.sin(dLat / 2), 2) + clt1 * clt2 * Math.pow(Math.sin(dLon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return c * 6371;
    }

    public void upload(MultipartFile file) throws IOException, ParserConfigurationException, SAXException {

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        XmlDataHandler xmlDataHandler = new XmlDataHandler();
        saxParser.parse(file.getInputStream(), xmlDataHandler);

        UploadData result = xmlDataHandler.getUploadData();
        List<UploadCityDto> cityDtos = result.getCityDtos();
        List<UploadDistanceDto> distanceDtos = result.getDistanceDtos();
        for (UploadCityDto dto: cityDtos) {
            cityRepository.saveAndFlush(
                    new City(
                            dto.getName(),
                            dto.getLatitude(),
                            dto.getLongitude())
            );
        }
        for (UploadDistanceDto dto: distanceDtos){
            distanceRepository.saveAndFlush(
                    new Distance(
                            cityRepository.findCityByName(dto.getFromCityName()).orElseThrow(() -> new CityNotFoundException("City with name " + dto.getFromCityName() + " is not found")),
                            cityRepository.findCityByName(dto.getToCityName()).orElseThrow(() -> new CityNotFoundException("City with name " + dto.getFromCityName() + " is not found")),
                            dto.getDistance()
                    )
            );
        }
    }
}
