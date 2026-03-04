package com.wipro.simplyfly.exceptions;

import java.time.LocalDate;

public class FlightNotFoundException extends RuntimeException {

    public FlightNotFoundException(String message) {
        super(message);
    }
    
    public FlightNotFoundException(String source,String destination ,LocalDate date) {
        super("No flights available for " + source + " to " + destination + " on " + date);
    }
}