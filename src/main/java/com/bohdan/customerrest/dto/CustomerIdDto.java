package com.bohdan.customerrest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerIdDto {
    @JsonProperty("id")
    Long id;

    @JsonProperty("fullName")
    String fullName;

    @JsonProperty("phone")
    String phone;

}
