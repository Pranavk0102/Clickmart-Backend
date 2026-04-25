package com.clickmart.backend.features.support.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TicketResponseRequest {

    @NotBlank(message = "Response text is required")
    @Size(max = 3000, message = "Response must be at most 3000 characters")
    private String response;

    public TicketResponseRequest() {}

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
