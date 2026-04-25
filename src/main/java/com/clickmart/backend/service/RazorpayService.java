package com.clickmart.backend.service;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Service
public class RazorpayService {

    private static final Logger log = LoggerFactory.getLogger(RazorpayService.class);

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    private RazorpayClient client;

    @PostConstruct
    public void init() {
        if (keyId == null || keyId.isBlank() || keySecret == null || keySecret.isBlank()) {
            log.warn("Razorpay credentials are not configured. Online payment via Razorpay will be unavailable.");
            return;
        }
        try {
            this.client = new RazorpayClient(keyId, keySecret);
            log.info("Razorpay client initialised successfully.");
        } catch (RazorpayException e) {
            log.error("Failed to initialise Razorpay client: {}. Online payment will be unavailable.", e.getMessage());
        }
    }

    public boolean isConfigured() {
        return client != null;
    }

    public String getKeyId() {
        return keyId;
    }

    public Order createOrder(int amountInPaise, String currency, String receipt) throws RazorpayException {
        if (!isConfigured()) {
            throw new RazorpayException("Razorpay is not configured on this server. Please set RAZORPAY_KEY_ID and RAZORPAY_KEY_SECRET environment variables.");
        }
        org.json.JSONObject orderRequest = new org.json.JSONObject();
        orderRequest.put("amount", amountInPaise);
        orderRequest.put("currency", currency);
        orderRequest.put("receipt", receipt);
        orderRequest.put("payment_capture", 1);
        return client.orders.create(orderRequest);
    }

    public boolean verifySignature(String orderId, String paymentId, String signature) {
        if (!isConfigured() || orderId == null || paymentId == null || signature == null) {
            return false;
        }
        try {
            String payload = orderId + "|" + paymentId;
            String generated = hmacSHA256(payload, keySecret);
            return generated.equals(signature);
        } catch (Exception ex) {
            log.error("Razorpay signature verification failed: {}", ex.getMessage());
            return false;
        }
    }

    private String hmacSHA256(String data, String key) throws Exception {
        Mac sha256Hmac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        sha256Hmac.init(secretKey);
        byte[] hash = sha256Hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        StringBuilder result = new StringBuilder();
        for (byte b : hash) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
}
