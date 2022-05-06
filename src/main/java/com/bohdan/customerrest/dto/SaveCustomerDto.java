package com.bohdan.customerrest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Pattern;

public class SaveCustomerDto {

    @JsonProperty("fullName")
    @Pattern(regexp = "^(\\w|\\s){2,50}$", message = "Input valid name(2..50 chars including whitespaces)")
    private String fullName;

    @JsonProperty("email")
    @Pattern(regexp = "^\\w{1,70}@\\w{1,20}\\.\\w{1,10}$",
            message = "Input email 2..100 chars, should include exactly one @ and one dot")
    private String email;

    @JsonProperty("phone")
    @Pattern(regexp = "^\\+\\d{6,14}$", message = "Input valid number 6..14 chars, only digits, should start from +")
    private String phone;
}
