package com.TradingApp.request;

import com.TradingApp.domain.VerificationType;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
public class ForgotPasswordTokenRequest {
    private String sendTo;
    private VerificationType verificationType;
}
