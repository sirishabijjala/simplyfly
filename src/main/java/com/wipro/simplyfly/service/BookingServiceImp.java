package com.wipro.simplyfly.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.simplyfly.dto.BookingRequestDTO;
import com.wipro.simplyfly.dto.BookingResponseDTO;
import com.wipro.simplyfly.dto.TransactionRequestDTO;
import com.wipro.simplyfly.entity.Booking;
import com.wipro.simplyfly.entity.Schedule;
import com.wipro.simplyfly.entity.User;
import com.wipro.simplyfly.repository.BookingRepository;
import com.wipro.simplyfly.repository.ScheduleRepository;
import com.wipro.simplyfly.repository.UserRepository;

import jakarta.transaction.Transactional;
@Service
public class BookingServiceImp implements IBookingService{
	@Autowired
	BookingRepository bookingRepository; 
  
    @Autowired
    UserRepository userRepository;
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    ITransactionService transactionService;
	@Override
	@Transactional
	public BookingResponseDTO createBooking(BookingRequestDTO request) {
		User user=userRepository.findById(request.getUserId()).orElseThrow(() -> new RuntimeException("User Not Found"));
		Schedule schedule=scheduleRepository.findById(request.getScheduleId()).orElseThrow(()-> new RuntimeException("Schedule Not Found"));
		if(schedule.getAvailableSeats()<request.getNumberOfSeats()) {
			throw new RuntimeException("Not Enough Seats Available");
		}
	    //  Reduce seats back to the schedule
		  schedule.setAvailableSeats(schedule.getAvailableSeats() - request.getNumberOfSeats());
		    scheduleRepository.save(schedule);
		    //Adds Booking
		Booking booking=new Booking();
		booking.setUser(user);
		booking.setSchedule(schedule);
		booking.setNumberOfSeats(request.getNumberOfSeats());
		booking.setBookingStatus("PENDING");
		booking.setBookingDate(LocalDateTime.now());
		booking.setBookingReference(UUID.randomUUID().toString());
		booking.setTotalAmount(schedule.getFare()*request.getNumberOfSeats());
		Booking saved=bookingRepository.save(booking);
		//Redirect to payment transaction
		TransactionRequestDTO transaction=new TransactionRequestDTO();
		transaction.setPaymentMethod(request.getPaymentMethod());
		transactionService.makePayment(saved.getId(), transaction);
		
	   
		return mapToResponse(saved);
	}

	@Override
	public BookingResponseDTO getBooking(Long bookingId) {
		Booking response=bookingRepository.findById(bookingId).orElseThrow(()->new RuntimeException("Booking Not Found"));
		return mapToResponse(response);
	}

	@Override
	public List<BookingResponseDTO> getAllBookings() {
		
		return  bookingRepository.findAll().stream().map(this::mapToResponse).toList();
	}

	@Override
	@Transactional
	public boolean cancelBooking(Long bookingId) {
		Booking booking = bookingRepository.findById(bookingId)
	            .orElseThrow(() -> new RuntimeException("Booking Not Found"));

	    // 1. Update booking status
	    booking.setBookingStatus("CANCELLED");
	    bookingRepository.save(booking);

	    // 2. Add seats back to the schedule
	    Schedule schedule = booking.getSchedule();
	    schedule.setAvailableSeats(schedule.getAvailableSeats() + booking.getNumberOfSeats());
	    scheduleRepository.save(schedule);

	    // 3. Refund the payment
	    transactionService.refundPayment(bookingId);
		return true;
	}
	public BookingResponseDTO mapToResponse(Booking booking) {
		BookingResponseDTO response=new BookingResponseDTO();
		response.setBookingId(booking.getId());
		response.setNumberOfSeats(booking.getNumberOfSeats());
		response.setBookingStatus(booking.getBookingStatus());
		response.setUserName(booking.getUser().getName());
		response.setBookingReference(booking.getBookingReference());
		response.setBookingDate(booking.getBookingDate());
		response.setTotalAmount(booking.getTotalAmount());
		response.setFlightName(booking.getSchedule().getFlight().getFlightName());
		response.setOrigin(booking.getSchedule().getRoute().getSource());
		response.setDestination(booking.getSchedule().getRoute().getDestination());
		
		return response;
	}

}
