package com.clickmart.backend.features.auth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.clickmart.backend.dto.ApiResponse;
import com.clickmart.backend.features.auth.dto.AuthResponse;
import com.clickmart.backend.features.auth.dto.LoginRequest;
import com.clickmart.backend.features.auth.dto.LogoutRequest;
import com.clickmart.backend.features.auth.dto.RefreshTokenRequest;
import com.clickmart.backend.features.auth.dto.RegisterRequest;
import com.clickmart.backend.features.auth.dto.UserDTO;
import com.clickmart.backend.features.auth.service.AuthService;
import com.clickmart.backend.features.profile.service.ProfileService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    private final ProfileService profileService;

    @Autowired
    public AuthController(AuthService authService,
                               ProfileService profileService) {
        this.authService = authService;
        this.profileService = profileService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(
            @Valid @RequestBody RegisterRequest request) {
        log.info("Register request received for email: {}", request.getEmail());
        AuthResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "User registered successfully", response));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request) {
        log.info("Login request received for email: {}", request.getEmail());
        AuthResponse response = authService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Login successful", response));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader,
            @RequestBody(required = false) LogoutRequest request) {
        log.info("Logout request received");
        authService.logout(authorizationHeader, request != null ? request.getRefreshToken() : null);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Logout successful", null));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<AuthResponse>> refreshToken(
            @Valid @RequestBody RefreshTokenRequest request) {
        log.info("Refresh token request received");
        AuthResponse response = authService.refreshToken(request.getRefreshToken());
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Token refreshed successfully", response));
    }

    
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserDTO>> me() {
        log.info("Get current user info request received");
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "User info", profileService.getProfile()));
    }
}
