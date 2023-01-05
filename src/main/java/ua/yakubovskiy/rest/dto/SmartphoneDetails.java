package ua.yakubovskiy.rest.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class SmartphoneDetails {

    private int id;

    private String name;

    private String colour;

}
