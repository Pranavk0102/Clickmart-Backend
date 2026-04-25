package com.clickmart.backend.features.profile.service;

import com.clickmart.backend.config.SecurityUtils;
import com.clickmart.backend.entity.User;
import com.clickmart.backend.exceptions.BadRequestException;
import com.clickmart.backend.features.auth.dto.UserDTO;
import com.clickmart.backend.features.auth.repository.UserRepository;
import com.clickmart.backend.features.profile.dto.UpdateProfileRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProfileService {

    private final SecurityUtils securityUtils;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ProfileService(SecurityUtils securityUtils,
                               UserRepository userRepository,
                               PasswordEncoder passwordEncoder) {
        this.securityUtils = securityUtils;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO getProfile() {
        return toDTO(securityUtils.getCurrentUser());
    }

    public UserDTO updateProfile(UpdateProfileRequest req) {
        User user = securityUtils.getCurrentUser();

        if (req.getPhone() != null && !req.getPhone().equals(user.getPhone())
                && userRepository.existsByPhone(req.getPhone())) {
            throw new BadRequestException("Phone number already in use");
        }

        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        if (req.getPhone() != null) user.setPhone(req.getPhone());

        if (req.getNewPassword() != null && !req.getNewPassword().isBlank()) {
            if (req.getConfirmPassword() == null || !req.getNewPassword().equals(req.getConfirmPassword()))
                throw new BadRequestException("New password and confirmation do not match");
            if (req.getCurrentPassword() == null || req.getCurrentPassword().isBlank())
                throw new BadRequestException("Current password is required to set a new password");
            if (!passwordEncoder.matches(req.getCurrentPassword(), user.getPassword()))
                throw new BadRequestException("Current password is incorrect");
            user.setPassword(passwordEncoder.encode(req.getNewPassword()));
        }

        return toDTO(userRepository.save(user));
    }

    private UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setRole(user.getRole() != null ? user.getRole().toString() : "CUSTOMER");
        dto.setActive(user.getActive());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }
}
