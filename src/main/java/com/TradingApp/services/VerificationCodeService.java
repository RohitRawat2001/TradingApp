package com.TradingApp.services;


import com.TradingApp.domain.VerificationType;
import com.TradingApp.modals.User;
import com.TradingApp.modals.VerificationCode;

public interface VerificationCodeService {

    VerificationCode sendVerificationCode(User user,VerificationType verificationType);

    VerificationCode getVerificationCodeById(Long id) throws Exception;

    VerificationCode getVerificationCodeByUser(Long userId);

    void deleteVerificationCodeById(VerificationCode verificationCode);

}
