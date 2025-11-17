package com.dachi.sellerapp.dto;

/**
 * Encapsulates user registration form data (first name, last name, email, password).
 */
public record RegistrationRequest(
        String firstName,
        String lastName,
        String email,
        String password
) {}

