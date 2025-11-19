package com.dachi.sellerapp.service;

import com.dachi.sellerapp.dto.RegistrationRequest;
import com.dachi.sellerapp.model.Role;
import com.dachi.sellerapp.model.User;
import com.dachi.sellerapp.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public void registerSeller(RegistrationRequest req) {

        if (userRepository.existsByEmail(req.email()))
            throw new RuntimeException("Email already exists");

        User u = new User();
        u.setFirstName(req.firstName());
        u.setLastName(req.lastName());
        u.setEmail(req.email());
        u.setPassword(passwordEncoder.encode(req.password()));
        u.setRole(Role.SELLER);
        //u.setCreatedAt(LocalDateTime.now()); // this is automatically set from model itself
        u.setEmailConfirmed(false); // TODO: Implement confirmation: this should be false
        u.setAdminApproved(false); // TODO: Implement confirmation: this should be false

        userRepository.save(u);

        // TODO: Implement confirmation
        /*String code = UUID.randomUUID().toString();
        redisService.store("confirm:" + code, u.getEmail(), 24h);

        emailService.send(
                u.getEmail(),
                "Confirm your account",
                "Click here: https://your-site.com/auth/confirm?code=" + code
        );*/
    }

}

