package ua.yakubovskiy.rest.dto;

import lombok.extern.jackson.Jacksonized;

@Jacksonized
public record RestResponse(String result) {

}