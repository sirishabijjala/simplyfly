package com.wipro.simplyfly.service;

import java.util.List;

import com.wipro.simplyfly.dto.BookingRequestDTO;
import com.wipro.simplyfly.dto.BookingResponseDTO;

public interface IBookingService {
	public BookingResponseDTO createBooking(BookingRequestDTO request);
	public BookingResponseDTO getBooking(Long bookingId);
	public List<BookingResponseDTO> getAllBookings();
	public void cancelBooking(Long bookingId);
	List<BookingResponseDTO> getBookingsByLoggedInUser();


	  
}
