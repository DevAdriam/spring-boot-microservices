package com.example.ecommerce.order_service.domain.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer{
        @JsonProperty("name")
        @NotBlank(message = "Customer Name  is required")
        private  String name;

        @JsonProperty("email")
        @Email(message = "email format is not valid")
        @NotBlank(message = "email is required")
        private  String email;

        @JsonProperty("phone")
        @NotBlank(message = "phone is required")
        private  String phone;
}