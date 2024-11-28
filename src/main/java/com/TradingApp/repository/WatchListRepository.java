package com.TradingApp.repository;

import com.TradingApp.modals.WatchList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchListRepository extends JpaRepository<WatchList,Long> {

    WatchList findByUserId(Long userId);

}
