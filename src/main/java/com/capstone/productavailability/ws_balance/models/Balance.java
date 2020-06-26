package com.capstone.productavailability.ws_balance.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@Entity
public class Balance implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer id;
    @NotNull(message = "Balance is required")
    Integer balance;
    @NotNull(message = "Product is required")
    Integer productId;
    @NotNull(message = "Location is required")
    Integer locationId;

    public Balance(){ }
}
