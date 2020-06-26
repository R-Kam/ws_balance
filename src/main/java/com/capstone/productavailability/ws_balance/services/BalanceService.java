package com.capstone.productavailability.ws_balance.services;

import com.capstone.productavailability.domain.product_availability_domain.LocationDTO;
import com.capstone.productavailability.domain.product_availability_domain.ProductDTO;
import com.capstone.productavailability.ws_balance.client.LocationClient;
import com.capstone.productavailability.ws_balance.client.ProductClient;
import com.capstone.productavailability.ws_balance.models.Balance;
import com.capstone.productavailability.ws_balance.repositories.BalanceRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.*;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class BalanceService {
    @Autowired
    BalanceRepository balanceRepository;
    @Autowired
    LocationClient locationClient;
    @Autowired
    ProductClient productClient;
    public ResponseEntity<String> saveBalance(Balance balance) {
        String message = "";
        ProductDTO productDTO = productClient.getProduct(balance.getProductId());
        LocationDTO locationDTO = locationClient.getLocation(balance.getLocationId());
        if(Objects.isNull(productDTO)){
             message = "No Product record to this ID : "+balance.getProductId();
            log.error(message);
            return new ResponseEntity<>(message,HttpStatus.FAILED_DEPENDENCY);
        }
        if(Objects.isNull(locationDTO)){
             message ="No Location record to this ID : "+balance.getLocationId();
                    log.error(message);
            return new ResponseEntity<>(message, HttpStatus.FAILED_DEPENDENCY);
        }
        try{
            Balance bal = balanceRepository.save(balance);
             message = String.valueOf(bal.getId());
             log.info("Balance saved : {}", bal);
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }catch (Exception e){
            message = "No Location record to this ID : "+balance.getLocationId();
            log.error(message);
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }

    }

    public ResponseEntity<String> updateBalance(Balance balance) {
        String message = "";
        ProductDTO productDTO = productClient.getProduct(balance.getProductId());
        if(Objects.isNull(productDTO)){
            message = "No Product record to this ID : "+balance.getProductId();
            log.error(message);
            return new ResponseEntity<>(message,HttpStatus.FAILED_DEPENDENCY);
        }
        try{
            Balance bal = balanceRepository.save(balance);
            message = String.valueOf(bal.getId());
            log.info("Balance updated succesfully");
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }catch (Exception e){
            message = "Server Error during the process to this ID : "+balance.getLocationId();
            log.error(message);
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity<List<Balance>> filterBalance(JSONObject filterby) {
        String productId = (String) filterby.get("productId");
        String departmentId = (String) filterby.get("departmentId");
        List<Balance> balances = null;
//        ExampleMatcher matcher = ExampleMatcher.matchingAll().withMatcher("firstName", contains().ignoreCase());
        if(Strings.isNotBlank(productId)) //TO DO {
        {
            Integer id = Integer.valueOf(productId);
            balances = balanceRepository.findBalancesByproductId(id);
        }else if(Strings.isNotBlank(departmentId)){  // TO DO : Collect the list of products from the dept and fetch the balances
                ;
            }

        return new ResponseEntity<>(balances, HttpStatus.OK);

    }
}
