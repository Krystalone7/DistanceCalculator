package com.artyom.dto.calculation;

import com.artyom.dto.DistanceDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CalcDto {
    private final List<DistanceDto> result;
}
