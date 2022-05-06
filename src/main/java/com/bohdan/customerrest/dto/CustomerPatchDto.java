package com.bohdan.customerrest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;

import javax.validation.constraints.Pattern;

public class CustomerPatchDto {

        @JsonProperty("id")
        @NotNull
        private Long id;

        @JsonProperty("fullName")
        @Pattern(regexp = "^\\w+\\s?\\w+$", message = "Input valid name")
        private String fullName;

        @Pattern(regexp = "^\\+\\d{6,14}$", message = "Input valid number")
        @JsonProperty("phone")
        private String phone;

}
