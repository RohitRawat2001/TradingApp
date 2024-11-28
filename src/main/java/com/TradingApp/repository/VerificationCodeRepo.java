package com.TradingApp.repository;

import com.TradingApp.modals.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodeRepo extends JpaRepository<com.TradingApp.modals.VerificationCode,Long> {

    public VerificationCode findByUserId(Long userId);
}
