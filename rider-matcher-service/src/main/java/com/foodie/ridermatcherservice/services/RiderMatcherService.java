package com.foodie.ridermatcherservice.services;

import com.foodie.commons.dto.RiderMatchRequestEvent;

/**
 * Created on 07/06/25.
 *
 * @author : aasif.raza
 */
public interface RiderMatcherService {
    void assignNearestRider(RiderMatchRequestEvent event);
}
