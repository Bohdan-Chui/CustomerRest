package com.bohdan.customerrest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class CustomerPatchDto {

        @JsonProperty("id")
        @NotNull
        private Long id;

        @JsonProperty("fullName")
        @Pattern(regexp = "^(\\w|\\s){2,50}$", message = "Input valid name(2..50 chars including whitespaces)")
        private String fullName;

        @Pattern(regexp = "^\\+\\d{6,14}$", message = "Input valid number 6..14 chars, only digits, should start from +")
        @JsonProperty("phone")
        private String phone;

}
