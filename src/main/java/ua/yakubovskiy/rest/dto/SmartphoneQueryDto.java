package ua.yakubovskiy.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmartphoneQueryDto {

    private String colour;

    private int brandId;

    private int size;

    private int from;
}
