package com.bohdan.customerrest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Pattern;

public class SaveCustomerDto {

    @JsonProperty("fullName")
    @Pattern(regexp = "^\\w+\\s?\\w+$", message = "Input valid name")
    private String fullName;

    @JsonProperty("email")
    @Pattern(regexp = "^\\w+@\\w+.\\w+$", message = "input email with example[example@mail.ua]")
    private String email;

    @JsonProperty("phone")
    @Pattern(regexp = "^\\+\\d{6,14}$", message = "Input valid number")
    private String phone;
}
