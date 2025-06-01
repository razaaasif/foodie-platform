package com.foodie.riderservice.services;

import com.foodie.commons.dto.OrderPreparedEvent;

/**
 * Created on 29/05/25.
 *
 * @author : aasif.raza
 */
public interface RiderService {
    void assignRider(OrderPreparedEvent event) ;
    void deliverOrder(Long orderId, String riderId);

    void takeOrder(Long orderId, String riderId);
}
