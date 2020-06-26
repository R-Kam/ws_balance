package com.capstone.productavailability.ws_balance.controller;

import com.capstone.productavailability.domain.product_availability_domain.LocationDTO;
import com.capstone.productavailability.domain.product_availability_domain.ProductDTO;
import com.capstone.productavailability.ws_balance.WsBalanceApplication;
import com.capstone.productavailability.ws_balance.client.LocationClient;
import com.capstone.productavailability.ws_balance.client.ProductClient;
import com.capstone.productavailability.ws_balance.models.Balance;
import com.capstone.productavailability.ws_balance.repositories.BalanceRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import java.util.Arrays;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {WsBalanceApplication.class, H2Config.class})
public class BalanceControllerIT {
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @MockBean
    LocationClient locationClient;
    @MockBean
    ProductClient productClient;
    @MockBean
    BalanceRepository balanceRepository;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void saveBalance() throws Exception{

//        balanceRepository.save(balance);
//        List<Balance> bal = balanceRepository.findAll();
//        Assert.assertEquals(1, bal.size());
//        Integer i = bal.get(0).getBalance();
//        Assert.assertEquals(Integer.valueOf(2), i);

        Balance balance = Balance.builder().balance(2).locationId(1).productId(2).build();

        Mockito.when(productClient.getProduct(2))
                .thenReturn(new ProductDTO(2,"",1,""));

        Mockito.when(locationClient.getLocation(1))
                .thenReturn(new LocationDTO(1,"Block","Street","zipCode","city"));

        mockMvc.perform(post("/saveBalance")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(balance))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$[0].id", Matchers.is(1)));
    }

    @Test
    public void filterBalance()throws Exception{

        Mockito.when(balanceRepository.findBalancesByproductId(1)).thenReturn(Arrays.asList(Balance.builder().balance(2).locationId(1).locationId(1).build()));
        mockMvc.perform(get("/filterBalanceItems")
                .contentType(MediaType.APPLICATION_JSON).content("{\"productId\":\"1\",\"departmentId\":\"1\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", IsCollectionWithSize.hasSize(1)))
                .andExpect(jsonPath("$[0].balance", Matchers.is(2)));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
