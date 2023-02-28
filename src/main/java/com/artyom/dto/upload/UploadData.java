package com.artyom.dto.upload;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UploadData {
    private List<UploadCityDto> cityDtos;
    private List<UploadDistanceDto> distanceDtos;
}
