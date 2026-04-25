package com.clickmart.backend.features.profile.controller;

import com.clickmart.backend.dto.ApiResponse;
import com.clickmart.backend.features.auth.dto.UserDTO;
import com.clickmart.backend.features.profile.dto.UpdateProfileRequest;
import com.clickmart.backend.features.profile.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    
    @GetMapping
    public ResponseEntity<ApiResponse<UserDTO>> getProfile() {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Profile fetched", profileService.getProfile()));
    }

    
    @PutMapping
    public ResponseEntity<ApiResponse<UserDTO>> updateProfile(@Valid @RequestBody UpdateProfileRequest req) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "Profile updated", profileService.updateProfile(req)));
    }
}
