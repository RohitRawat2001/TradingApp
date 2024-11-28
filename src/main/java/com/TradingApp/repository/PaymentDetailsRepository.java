package com.TradingApp.repository;

import com.TradingApp.modals.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails,Long> {

    PaymentDetails findByUserId(Long userId);
}
