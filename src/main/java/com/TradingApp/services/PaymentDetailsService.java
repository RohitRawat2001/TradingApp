package com.TradingApp.services;

import com.TradingApp.modals.PaymentDetails;
import com.TradingApp.modals.User;

public interface PaymentDetailsService {

    public PaymentDetails addPaymentDetails(String accountNumber,
                                            String accountHolderName,
                                            String ifsc,
                                            String bankName,
                                            User user);

    PaymentDetails getUsersPaymentDetails(User user);

}
