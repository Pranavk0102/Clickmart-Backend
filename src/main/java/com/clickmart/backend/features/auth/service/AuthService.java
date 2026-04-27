package com.clickmart.backend.features.auth.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clickmart.backend.config.CustomUserDetailsService;
import com.clickmart.backend.config.JwtUtil;
import com.clickmart.backend.entity.User;
import com.clickmart.backend.enums.Role;
import com.clickmart.backend.exceptions.BadRequestException;
import com.clickmart.backend.features.auth.dto.AuthResponse;
import com.clickmart.backend.features.auth.dto.LoginRequest;
import com.clickmart.backend.features.auth.dto.RegisterRequest;
import com.clickmart.backend.features.auth.dto.UserDTO;
import com.clickmart.backend.features.auth.repository.UserRepository;

@Service
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    private final TokenRevocationService tokenRevocationService;
    private final com.clickmart.backend.features.auth.repository.PasswordResetTokenRepository passwordResetTokenRepository;
    private final com.clickmart.backend.service.EmailService emailService;

    @Autowired
    public AuthService(UserRepository userRepository, 
                       PasswordEncoder passwordEncoder, 
                       JwtUtil jwtUtil, 
                       CustomUserDetailsService userDetailsService, 
                       TokenRevocationService tokenRevocationService,
                       com.clickmart.backend.features.auth.repository.PasswordResetTokenRepository passwordResetTokenRepository,
                       com.clickmart.backend.service.EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.tokenRevocationService = tokenRevocationService;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.emailService = emailService;
    }

    public AuthResponse register(RegisterRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new BadRequestException("Passwords do not match");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already registered");
        }

        if (userRepository.existsByPhone(request.getPhone())) {
            throw new BadRequestException("Phone number already registered");
        }

        User user = new User();
        BeanUtils.copyProperties(request, user);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.CUSTOMER);
        user.setActive(true);
        user = userRepository.save(user);

        return buildAuthResponse(user);
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("Invalid email or password"));

        if (!user.getActive()) {
            throw new BadRequestException("User account is inactive");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid email or password");
        }

        return buildAuthResponse(user);
    }

    public AuthResponse refreshToken(String refreshToken) {
        if (tokenRevocationService.isRevoked(refreshToken)) {
            throw new BadRequestException("Invalid or expired refresh token");
        }

        String username;
        try {
            username = jwtUtil.extractUsername(refreshToken);
        } catch (Exception ex) {
            throw new BadRequestException("Invalid or expired refresh token");
        }

        org.springframework.security.core.userdetails.UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (!jwtUtil.validateRefreshToken(refreshToken, userDetails)) {
            throw new BadRequestException("Invalid or expired refresh token");
        }

        String newToken = jwtUtil.generateToken(userDetails);
        String newRefreshToken = jwtUtil.generateRefreshToken(userDetails);
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new BadRequestException("User not found"));
        tokenRevocationService.revokeToken(refreshToken);

        AuthResponse response = new AuthResponse();
        response.setUser(convertToUserDTO(user));
        response.setToken(newToken);
        response.setRefreshToken(newRefreshToken);
        response.setType("Bearer");
        return response;
    }

    public void logout(String authorizationHeader, String refreshToken) {
        String accessToken = extractBearerToken(authorizationHeader);
        if ((accessToken == null || accessToken.isBlank()) && (refreshToken == null || refreshToken.isBlank())) {
            throw new BadRequestException("Provide an access token or refresh token to log out");
        }

        tokenRevocationService.revokeToken(accessToken);
        tokenRevocationService.revokeToken(refreshToken);
    }

    public void forgotPassword(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null || !user.getActive()) {
            return; // Fail silently for security
        }

        passwordResetTokenRepository.findByUser(user).ifPresent(t -> {
            passwordResetTokenRepository.delete(t);
            passwordResetTokenRepository.flush(); // Force delete to happen before insert to avoid unique constraint violation
        });

        String otp = String.format("%06d", new java.util.Random().nextInt(999999));
        
        com.clickmart.backend.entity.PasswordResetToken token = new com.clickmart.backend.entity.PasswordResetToken(
                otp, user, java.time.LocalDateTime.now().plusMinutes(10)
        );
        passwordResetTokenRepository.save(token);

        // Send email with OTP
        emailService.sendPasswordResetEmail(user.getEmail(), otp);
    }

    public void resetPassword(String tokenString, String newPassword) {
        com.clickmart.backend.entity.PasswordResetToken token = passwordResetTokenRepository.findByToken(tokenString)
                .orElseThrow(() -> new BadRequestException("Invalid or expired password reset token"));

        if (token.isExpired()) {
            passwordResetTokenRepository.delete(token);
            throw new BadRequestException("Invalid or expired password reset token");
        }

        User user = token.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        passwordResetTokenRepository.delete(token);
    }

    private AuthResponse buildAuthResponse(User user) {
        org.springframework.security.core.userdetails.UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtUtil.generateToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);

        AuthResponse response = new AuthResponse();
        response.setUser(convertToUserDTO(user));
        response.setToken(token);
        response.setRefreshToken(refreshToken);
        response.setType("Bearer");
        return response;
    }

    private UserDTO convertToUserDTO(User user) {
        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(user, dto);
        if (user.getRole() != null) {
            dto.setRole(user.getRole().toString());
        } else {
            dto.setRole("CUSTOMER");
        }
        return dto;
    }

    private String extractBearerToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }
}
