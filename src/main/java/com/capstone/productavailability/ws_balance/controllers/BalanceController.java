package com.capstone.productavailability.ws_balance.controllers;

import com.capstone.productavailability.ws_balance.models.Balance;
import com.capstone.productavailability.ws_balance.services.BalanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
public class BalanceController {
    @Autowired
    BalanceService balanceService;

    @PostMapping("/saveBalance")
    public ResponseEntity<String> saveBalance(@RequestBody @Valid Balance balance){
        log.info("save balance : {}", balance);
        return  balanceService.saveBalance(balance);
    }

    @PutMapping("/updateBalance")
    public ResponseEntity<String> updateBalance(@RequestBody @Valid Balance balance){
        log.info("Update Balance : {}", balance);
        return  balanceService.updateBalance(balance);
    }

    @GetMapping("/filterBalanceItems")
    public ResponseEntity<List<Balance>> findBalanceItem(@RequestBody JSONObject filterby){
        log.info("Department : {}", filterby);
        return  balanceService.filterBalance(filterby);
    }
}
