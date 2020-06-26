package com.capstone.productavailability.ws_balance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
@Slf4j
public class WsBalanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WsBalanceApplication.class, args);

       log.info("Ws Balance running +++++++++++++++++++++++++++++++++++++++++++++++++++++");

    }

}
