package com.bohdan.customerrest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerFullDto {
    @JsonProperty("id")
    Long id;

    @JsonProperty("fullName")
    String fullName;

    @JsonProperty("email")
    String email;

    @JsonProperty("phone")
    String phone;
}
