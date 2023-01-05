package ua.yakubovskiy.rest.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class SmartphoneSave {

    private String model;

    private String colour;

    private int brandId;
}
