package com.TradingApp.services;

import com.TradingApp.domain.VerificationType;
import com.TradingApp.modals.ForgotPasswordToken;
import com.TradingApp.modals.User;

public interface  ForgotPasswordService {

    ForgotPasswordToken createToken(User user, String id, String otp, VerificationType verificationType,String sendTo);
    ForgotPasswordToken findById(String id);
    ForgotPasswordToken findByUser(Long userId);
    void deleteToken(ForgotPasswordToken token);

}
