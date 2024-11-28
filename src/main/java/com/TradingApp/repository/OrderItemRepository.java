package com.TradingApp.repository;

import com.TradingApp.modals.Order;
import com.TradingApp.modals.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
}
