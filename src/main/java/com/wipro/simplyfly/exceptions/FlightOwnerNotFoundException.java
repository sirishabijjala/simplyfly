package com.wipro.simplyfly.exceptions;

public class FlightOwnerNotFoundException extends RuntimeException{
	
	public FlightOwnerNotFoundException(Long id) {
        super("FlightOwner not found with id: " + id);
    }
}
