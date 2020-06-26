package com.capstone.productavailability.ws_balance.repositories;

import com.capstone.productavailability.ws_balance.models.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface BalanceRepository extends JpaRepository<Balance, Integer> {
//    List<Balance> findBalancesByproductIdandlocationId(Integer productId, Integer locationId);
    List<Balance> findBalancesByproductId(Integer productId);
}
