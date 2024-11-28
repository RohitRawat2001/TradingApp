package com.TradingApp.services;

import jakarta.mail.MessagingException;

public interface EmailService {

    void sendVerificationOtpEmail(String userEmail,String otp) throws MessagingException;

}
