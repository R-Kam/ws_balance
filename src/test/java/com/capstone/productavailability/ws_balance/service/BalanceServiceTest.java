package com.capstone.productavailability.ws_balance.service;

import com.capstone.productavailability.domain.product_availability_domain.LocationDTO;
import com.capstone.productavailability.domain.product_availability_domain.ProductDTO;
import com.capstone.productavailability.ws_balance.WsBalanceApplication;
import com.capstone.productavailability.ws_balance.WsBalanceApplicationTests;
import com.capstone.productavailability.ws_balance.client.LocationClient;
import com.capstone.productavailability.ws_balance.client.ProductClient;
import com.capstone.productavailability.ws_balance.controller.H2Config;
import com.capstone.productavailability.ws_balance.models.Balance;
import com.capstone.productavailability.ws_balance.repositories.BalanceRepository;
import com.capstone.productavailability.ws_balance.services.BalanceService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WsBalanceApplication.class, H2Config.class})
//@WebAppConfiguration
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BalanceServiceTest {

    @Autowired
    BalanceService balanceService;

    @Resource
    BalanceRepository balanceRepository;

    @MockBean
    LocationClient locationClient;
    @MockBean
    ProductClient productClient;

    @Before
    public void setup() throws Exception {
    }

    @Test
    public void saveBalanceSucceed(){
//Setup
        Mockito.when(locationClient.getLocation(1))
                .thenReturn(new LocationDTO(1,"Block","Street","zipCode","city"));
        Mockito.when(productClient.getProduct(2)).thenReturn(new ProductDTO(2,"",1,""));

//Act
        Balance balance = Balance.builder().balance(2).locationId(1).productId(2).build();
        ResponseEntity<String>  responseEntity = balanceService.saveBalance(balance);
//Assert
        List<Balance> bal = balanceRepository.findAll();
        Assert.assertEquals(1, bal.size());
        Integer i = bal.get(0).getBalance();
        Assert.assertEquals(Integer.valueOf(2), i);

        Assert.assertEquals(HttpStatus.CREATED,responseEntity.getStatusCode());
    }

    @Test
    public void saveBalancFailOnLocation(){
//        Setup
        Mockito.when(locationClient.getLocation(1))
                .thenReturn(null);
        Mockito.when(productClient.getProduct(2)).thenReturn(new ProductDTO(2,"",1,""));

        Balance balance = Balance.builder().balance(2).locationId(1).productId(2).build();
        ResponseEntity<String>  responseEntity = balanceService.saveBalance(balance);

        List<Balance> bal = balanceRepository.findAll();
        Assert.assertEquals(0, bal.size());


        Assert.assertEquals(HttpStatus.FAILED_DEPENDENCY,responseEntity.getStatusCode());
    }
}
