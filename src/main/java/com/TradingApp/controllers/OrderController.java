package com.TradingApp.controllers;

import com.TradingApp.domain.OrderType;
import com.TradingApp.modals.Coin;
import com.TradingApp.modals.Order;
import com.TradingApp.modals.User;
import com.TradingApp.request.CreateOrderRequest;
import com.TradingApp.services.CoinService;
import com.TradingApp.services.OrderService;
import com.TradingApp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final CoinService coinService;
//    private final WalletTransactionService walletTransactionService;

    @PostMapping("/pay")
    public ResponseEntity<Order> payOrderPayment(@RequestHeader("Authorization") String jwt,
                                                 @RequestBody CreateOrderRequest req) throws Exception {
        User user =  userService.findUserProfileByJwt(jwt);
        Coin coin = coinService.findById(req.getCoinId());

        Order order = orderService.processOrder(coin,req.getQuantity(),req.getOrderType(),user);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@RequestHeader("Authorization") String jwt,
                                              @PathVariable Long orderId) throws Exception {
       if(jwt == null){
           throw new Exception("token missing...");
       }
       User user = userService.findUserProfileByJwt(jwt);

       Order order = orderService.getOrderById(orderId);
       if(order.getUser().getId().equals(user.getId())){
           return ResponseEntity.ok(order);
       }else{
           throw new Exception("you don't have access");
       }
    }

    @GetMapping()
    public ResponseEntity<List<Order>> getAllOrdersForUser(@RequestHeader("Authorization") String jwt,
                                                           @RequestParam(required = false) OrderType order_type,
                                                           @RequestParam(required = false) String asset_symbol) throws Exception {
         Long userId = userService.findUserProfileByJwt(jwt).getId();
         List<Order> userOrders = orderService.getAllOrdersOfUser(userId,order_type,asset_symbol);
         return ResponseEntity.ok(userOrders);
    }

}
