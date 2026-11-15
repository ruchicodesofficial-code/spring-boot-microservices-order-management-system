package com.ruchicodes.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(

        @NotBlank(message = "Name is required")
        @Size(max = 100,message = "Name cannot exceed 100 characters")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "please provide a valid email")
        @Size(max = 150,message = "Email cannot exceed 150 characters")
        String email,

        @Size(max=20,message = "phone number cannot exceed 20 characters")
        String phone
) {
}
