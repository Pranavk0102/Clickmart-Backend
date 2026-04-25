package com.clickmart.backend.features.support.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TicketRequest {
    @NotBlank(message = "Subject is required")
    @Size(max = 255)
    private String subject;

    @NotBlank(message = "Category is required")
    private String category;

    private String priority = "medium";

    private String relatedOrderNumber;

    @NotBlank(message = "Message is required")
    @Size(max = 3000)
    private String message;

    public TicketRequest() {}
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }
    public String getRelatedOrderNumber() { return relatedOrderNumber; }
    public void setRelatedOrderNumber(String relatedOrderNumber) { this.relatedOrderNumber = relatedOrderNumber; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
