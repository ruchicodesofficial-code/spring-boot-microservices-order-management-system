package com.ruchicodes.userservice.dto;

public record UserResponse(
        Long id,
        String name,
        String email,
        String phone
) {
}
