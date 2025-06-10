package com.foodie.riderservice.services;

import com.foodie.commons.dto.OrderPreparedEvent;
import com.foodie.riderservice.dto.RiderDetailDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * Created on 29/05/25.
 *
 * @author : aasif.raza
 */
public interface RiderService {
    void assignRider(OrderPreparedEvent event);

    void deliverOrder(Long orderId, String riderId);

    void takeOrder(Long orderId, String riderId);

    RiderDetailDTO createRider(@Valid @NotNull RiderDetailDTO riderDetailDTO);
}
