package com.artyom.dto.upload;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Setter
@Getter
public class UploadCityDto {
    private String name;
    private BigDecimal latitude;
    private BigDecimal longitude;
}
