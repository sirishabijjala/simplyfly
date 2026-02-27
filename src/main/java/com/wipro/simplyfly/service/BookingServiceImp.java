package com.wipro.simplyfly.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.simplyfly.dto.BookingRequestDTO;
import com.wipro.simplyfly.dto.BookingResponseDTO;
import com.wipro.simplyfly.entity.User;
import com.wipro.simplyfly.repository.BookingRepository;
import com.wipro.simplyfly.repository.UserRepository;
@Service
public class BookingServiceImp implements IBookingService{
	@Autowired
	BookingRepository bookingRepository; 
   
   ;
    @Autowired
    UserRepository userRepository;
	@Override
	public BookingResponseDTO createBooking(BookingRequestDTO request) {
		User user=userRepository.findById(request.getId()).orElseThrow(() -> new RuntimeException("User Not Found"));
		
		
		return null;
	}

	@Override
	public BookingResponseDTO getBooking(Long bookingId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BookingResponseDTO> getAllBookings() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean cancelBooking(Long bookingId) {
		// TODO Auto-generated method stub
		return false;
	}

}
