package com.artyom.dto.calculation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class CalcInputDto {
    private final String calcType;
    private final List<String> fromCityNames;
    private final List<String> toCityNames;

    @JsonCreator
    public CalcInputDto(@JsonProperty("Calculation Type") String calcType,
                        @JsonProperty("From City") List<String> fromCityNames,
                        @JsonProperty("To City") List<String> toCityNames) {
        this.calcType = calcType;
        this.fromCityNames = fromCityNames;
        this.toCityNames = toCityNames;
    }
}
