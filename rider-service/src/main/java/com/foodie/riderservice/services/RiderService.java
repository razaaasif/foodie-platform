package com.foodie.riderservice.services;

import com.foodie.riderservice.dto.OrderPreparedEvent;

/**
 * Created on 29/05/25.
 *
 * @author : aasif.raza
 */
public interface RiderService {
    void assignRider(OrderPreparedEvent event) ;
    void deliverOrder(String orderId, String riderId, String deliveryTime);

    void takeOrder(String orderId, String riderId, String deliveryTime);
}
