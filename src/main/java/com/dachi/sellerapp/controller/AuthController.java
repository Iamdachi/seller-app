package com.dachi.sellerapp.controller;

import com.dachi.sellerapp.dto.RegistrationRequest;
import com.dachi.sellerapp.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles user registration, login, email confirmation, and password reset endpoints.
 */
@RestController
@RequestMapping("/auth")   // optional but recommended
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequest req) {
        authService.registerSeller(req);
        return ResponseEntity.ok("Check your email for confirmation link.");
    }
}
