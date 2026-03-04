package com.wipro.simplyfly.exceptions;

public class RouteNotFoundException extends RuntimeException {
    public RouteNotFoundException(Integer id) {
        super("Route not found with id: " + id);
    }
}