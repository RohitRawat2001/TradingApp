package com.TradingApp.controllers;

import com.TradingApp.modals.Coin;
import com.TradingApp.modals.User;
import com.TradingApp.modals.WatchList;
import com.TradingApp.services.CoinService;
import com.TradingApp.services.UserService;
import com.TradingApp.services.WatchListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/watchList")
@RequiredArgsConstructor
public class WatchListController {

    private final WatchListService watchListService;
    private final UserService userService;
    private final CoinService coinService;

    @GetMapping("/user")
    public ResponseEntity<WatchList> getUserWatchList(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        WatchList watchList = watchListService.findUserWatchList(user.getId());
        return ResponseEntity.ok(watchList);
    }

    @GetMapping("/{watchListId}")
    public ResponseEntity<WatchList> getWatchListById(@PathVariable Long watchListId) throws Exception {
        WatchList byId = watchListService.findById(watchListId);
        return ResponseEntity.ok(byId);
    }

    @PatchMapping("/add/coin/{coinId}")
    public ResponseEntity<Coin> addItemToWatchList(@RequestHeader("Authorization") String jwt,
                                                   @PathVariable String coinId) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Coin coin = coinService.findById(coinId);
        Coin addedCoin = watchListService.addItemToWatchList(coin,user);
        return ResponseEntity.ok(addedCoin);
    }

}
