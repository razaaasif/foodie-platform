package com.foodie.commons.constants;

/**
 * Created on 01/06/25.
 *
 * @author : aasif.raza
 */
public enum RestaurantStatus {

    OPEN("Restaurant is open and accepting orders."),
    CLOSED("Restaurant is closed and not accepting orders."),
    BUSY("Restaurant is open but at high capacity (delays possible)."),
    MAINTENANCE("Restaurant is temporarily closed for maintenance."),
    HOLIDAY("Restaurant is closed for a holiday.");

    private final String description;

    RestaurantStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public boolean isOperational() {
        return this == OPEN || this == BUSY; // Only allow orders in these states
    }
}