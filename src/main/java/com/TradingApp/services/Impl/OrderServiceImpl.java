package com.TradingApp.services.Impl;

import com.TradingApp.domain.OrderStatus;
import com.TradingApp.domain.OrderType;
import com.TradingApp.modals.*;
import com.TradingApp.repository.OrderItemRepository;
import com.TradingApp.repository.OrderRepository;
import com.TradingApp.repository.WalletRepository;
import com.TradingApp.services.AssetService;
import com.TradingApp.services.OrderService;
import com.TradingApp.services.WalletService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final WalletService walletService;
    private final AssetService assetService;

    @Override
    public Order createOrder(User user, OrderItem orderItem, OrderType orderType) {
       double price = orderItem.getCoin().getCurrentPrice()*orderItem.getQuantity();

       Order order = new Order();
       order.setUser(user);
       order.setOrderItem(orderItem);
       order.setOrderType(orderType);
       order.setPrice(BigDecimal.valueOf(price));
       order.setTimestamp(LocalDateTime.now());
       order.setStatus(OrderStatus.PENDING);

       return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long orderId) throws Exception {
        return orderRepository.findById(orderId).orElseThrow(()-> new Exception("Order not found"));
    }

    @Override
    public List<Order> getAllOrdersOfUser(Long userId, OrderType orderType, String assetSymbol) {
        return orderRepository.findByUserId(userId);
    }

    public OrderItem createOrderItem(Coin coin,double quantity,double buyPrice,double sellPrice){
        OrderItem orderItem = new OrderItem();
        orderItem.setCoin(coin);
        orderItem.setQuantity(quantity);
        orderItem.setBuyPrice(buyPrice);
        orderItem.setSellPrice(sellPrice);
        return orderItemRepository.save(orderItem);
    }

    @Transactional
    public Order buyAsset(Coin coin,double quantity,User user) throws Exception {
        if(quantity <= 0){
            throw new Exception("Quantity should be > 0");
        }

        double buyPrice = coin.getCurrentPrice();

        OrderItem orderItem = createOrderItem(coin,quantity,buyPrice,0);

        Order order = createOrder(user,orderItem,OrderType.BUY);
        orderItem.setOrder(order);

        walletService.payOrderPayment(order,user);

        order.setStatus(OrderStatus.SUCCESS);
        order.setOrderType(OrderType.BUY);
        Order savedOrder = orderRepository.save(order);

        //create assets
        Asset oldAsset = assetService.findAssetByUserIdAndCoinId(order.getUser().getId(),
                order.getOrderItem().getCoin().getId());

        if(oldAsset == null){
            assetService.createAsset(user,orderItem.getCoin(),orderItem.getQuantity());
        }else{
            assetService.updateAsset(oldAsset.getId(),quantity);
        }
        return savedOrder;
    }

    @Transactional
    public Order sellAsset(Coin coin,double quantity,User user) throws Exception {
        if(quantity <= 0){
            throw new Exception("Quantity should be > 0");
        }

        double sellPrice = coin.getCurrentPrice();

        Asset assetToSell = assetService.findAssetByUserIdAndCoinId(user.getId(), coin.getId());

        if(assetToSell != null){

        double buyPrice = assetToSell.getBuyPrice();

        OrderItem orderItem = createOrderItem(coin,quantity,buyPrice,sellPrice);

        Order order = createOrder(user,orderItem,OrderType.SELL);
        orderItem.setOrder(order);

        if(assetToSell.getQuantity() >= quantity){
            order.setStatus(OrderStatus.SUCCESS);
            order.setOrderType(OrderType.SELL);
            Order savedOrder = orderRepository.save(order);

            walletService.payOrderPayment(order,user);

            Asset updatedAsset = assetService.updateAsset(assetToSell.getId(),-quantity);
            if(updatedAsset.getQuantity()*coin.getCurrentPrice()<=1){
                assetService.deleteAsset(updatedAsset.getId());
            }
            return savedOrder;
        }
      throw new Exception("Insufficient quantity to sell");
        } //create assets
       throw new Exception("asset not found");
    }

    @Override
    @Transactional
    public Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception {
        if(orderType == OrderType.BUY){
            return buyAsset(coin,quantity,user);
        }
        else if(orderType.equals(OrderType.SELL)){
            return sellAsset(coin,quantity,user);
        }
       throw new Exception("Insufficient order Type");
    }
}
