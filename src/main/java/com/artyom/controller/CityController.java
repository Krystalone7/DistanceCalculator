package com.artyom.controller;

import com.artyom.dto.CityDto;
import com.artyom.dto.calculation.CalcDto;
import com.artyom.dto.calculation.CalcInputDto;
import com.artyom.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CityController {

    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("cities")
    public ResponseEntity<List<CityDto>> showAll(){
        return new ResponseEntity<>(cityService.getAll(), HttpStatus.OK);
    }

    @PostMapping("calculate")
    public ResponseEntity<CalcDto> calculate(@RequestBody CalcInputDto calcInputDto){
        return new ResponseEntity<>(cityService.calculate(calcInputDto), HttpStatus.OK);
    }

    @PostMapping(path = "upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> upload(@RequestParam("file")MultipartFile multipartFile) throws IOException, ParserConfigurationException, SAXException {
        cityService.upload(multipartFile);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
