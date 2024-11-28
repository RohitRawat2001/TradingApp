package com.TradingApp.services;

import com.TradingApp.domain.OrderType;
import com.TradingApp.modals.Coin;
import com.TradingApp.modals.Order;
import com.TradingApp.modals.OrderItem;
import com.TradingApp.modals.User;

import java.util.List;

public interface OrderService {

    Order createOrder(User user, OrderItem orderItem, OrderType orderType);

    Order getOrderById(Long orderId) throws Exception;

    List<Order> getAllOrdersOfUser(Long userId,OrderType orderType,String assetSymbol);

    Order processOrder(Coin coin,double quantity,OrderType orderType,User user) throws Exception;
}