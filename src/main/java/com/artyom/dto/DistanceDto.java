package com.artyom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DistanceDto {
    private String fromCityName;
    private String toCityName;
    private Double crowflightDistance;
    private Double matrixDistance;
}
