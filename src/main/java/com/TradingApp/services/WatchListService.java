package com.TradingApp.services;

import com.TradingApp.modals.Coin;
import com.TradingApp.modals.User;
import com.TradingApp.modals.WatchList;

public interface WatchListService {

    WatchList findUserWatchList(Long userId) throws Exception;
    WatchList createWatchList(User user);
    WatchList findById(Long id) throws Exception;

    Coin addItemToWatchList(Coin coin,User user) throws Exception;

}
