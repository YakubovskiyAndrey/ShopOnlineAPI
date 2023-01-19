package ua.yakubovskiy.rest.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class BrandDetails {

    private int id;

    private String name;

}
