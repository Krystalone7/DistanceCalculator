package com.artyom.controller.advice;

import com.artyom.controller.advice.exceptions.CityNotFoundException;
import com.artyom.controller.advice.exceptions.DistanceNotFoundException;
import com.artyom.controller.advice.exceptions.Exception;
import com.artyom.controller.advice.exceptions.UnknownOperationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@RestControllerAdvice
public class CityControllerAdvice {


    @ExceptionHandler(CityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Exception> handleException(CityNotFoundException e){
        String code = "not_found_error";
        return new ResponseEntity<>(new Exception(code, e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DistanceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Exception> handleException(DistanceNotFoundException e){
        String code = "not_found_error";
        return new ResponseEntity<>(new Exception(code, e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnknownOperationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Exception> handleException(UnknownOperationException e){
        String code = "bad_request";
        return new ResponseEntity<>(new Exception(code, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Exception> handleException(IOException e){
        String code = "IO_exception";
        return new ResponseEntity<>(new Exception(code, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ParserConfigurationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Exception> handleException(ParserConfigurationException e){
        String code = "parser-configuration-exception";
        return new ResponseEntity<>(new Exception(code, e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(SAXException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Exception> handleException(SAXException e){
        String code = "IO_exception";
        return new ResponseEntity<>(new Exception(code, e.getMessage()), HttpStatus.CONFLICT);
    }
}