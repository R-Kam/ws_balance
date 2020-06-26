package com.capstone.productavailability.ws_balance.client;

import com.capstone.productavailability.domain.product_availability_domain.LocationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "Locations", url = "${locationService.baseUrl}")
@Service
public interface LocationClient {
    @GetMapping("/getLocation/{locationId}")
    LocationDTO getLocation(@PathVariable("locationId") Integer locationId);
}
