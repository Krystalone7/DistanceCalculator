package com.artyom.dto.upload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadDistanceDto {
    private String fromCityName;
    private String toCityName;
    private Double distance;
}
