package com.TradingApp.services;

import com.TradingApp.modals.Coin;

import java.util.List;

public interface CoinService {

    List<Coin> getCoinList(int page) throws Exception;

    String getMarketChart(String id,int days) throws Exception;

    String getCoinDetails(String coinId) throws Exception;

    Coin findById(String id) throws Exception;

    String searchCoin(String keyword) throws Exception;

    String getTop50CoinsByMarketCapRank() throws Exception;

    String getTreadingCoins() throws Exception;

}
