package com.wipro.simplyfly.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wipro.simplyfly.dto.BookingRequestDTO;
import com.wipro.simplyfly.dto.BookingResponseDTO;
@SpringBootTest
class BookingServiceImpTest  {
	@Autowired
	IBookingService bookingService;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	 @Test
	    void testGetBooking() {
	        BookingResponseDTO result = bookingService.getBooking(3L);

	        assertNotNull(result);
	        assertEquals(1, result.getNumberOfSeats());
	        assertEquals(2500, result.getTotalAmount());
	        assertNotNull(result.getUserName()); // lazy-safe now
	    }

	    @Test
	    void testCreateBooking() {
	        BookingRequestDTO request = new BookingRequestDTO(3L, 1L, 1, "UPI");
	        BookingResponseDTO response = bookingService.createBooking(request);

	        assertNotNull(response);
	        assertEquals(1, response.getNumberOfSeats());
	        assertTrue(response.getTotalAmount() > 0);
	    }
	

}
