package com.TradingApp.modals;

import com.TradingApp.domain.PaymentMethod;
import com.TradingApp.domain.PaymentOrderStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class PaymentOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long amount;

    private PaymentOrderStatus status = PaymentOrderStatus.PENDING;

    private PaymentMethod paymentMethod;
//
//    private String paymentLinkId;

    @ManyToOne
    private User user;

//    @OneToMany
//    private Set<Order> orders = new HashSet<>();
}
