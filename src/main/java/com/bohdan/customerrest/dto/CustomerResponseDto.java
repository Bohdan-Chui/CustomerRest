package com.bohdan.customerrest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerResponseDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("fullName")
    private String fullName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone")
    private String phone;
}
