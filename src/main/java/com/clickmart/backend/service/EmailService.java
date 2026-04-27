package com.clickmart.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username:noreply@clickmart.com}")
    private String fromEmail;

    public void sendPasswordResetEmail(String toEmail, String otp) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject("ClickMart - Your Password Reset OTP");
            message.setText("Hello,\n\n" +
                    "We received a request to reset your password. " +
                    "Please enter the following 6-digit OTP to set a new password:\n\n" +
                    otp + "\n\n" +
                    "This code will expire in 10 minutes. If you didn't request a password reset, please ignore this email.\n\n" +
                    "Regards,\nThe ClickMart Team");
            
            mailSender.send(message);
            logger.info("Password reset email sent to {}", toEmail);
        } catch (Exception e) {
            logger.error("Failed to send password reset email to {}: {}", toEmail, e.getMessage());
            // We don't throw an exception here because it might expose that the email exists or fail the API response,
            // but in a production app you might want to handle this differently.
        }
    }
}
