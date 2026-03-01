package com.wipro.simplyfly.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.simplyfly.dto.BookingRequestDTO;
import com.wipro.simplyfly.dto.BookingResponseDTO;
import com.wipro.simplyfly.service.IBookingService;

@RestController
@RequestMapping("/simplyfly/")
public class BookingRestController {
	@Autowired
	IBookingService bookingService;
	//create booking
	@PostMapping("BookTicket")
	public BookingResponseDTO userBooking(@Validated @RequestBody BookingRequestDTO bookingRequestDTO) {
		return bookingService.createBooking(bookingRequestDTO);
	}
	//fetch booking by id
	@GetMapping("GetById/{id}")
	public BookingResponseDTO getById(@PathVariable Long id ) {
		return  bookingService.getBooking(id);
	}
	//fetch all booking
	@GetMapping("GetAll")
	public List<BookingResponseDTO> getAllBooking(){
		return bookingService.getAllBookings();
		
	}
	//cancel booking
    @PutMapping("CancelBookingById/{id}")
    public String cancelBookingById(@PathVariable Long bookingId) {
    	bookingService.cancelBooking(bookingId);
    	return "Booking cancelled successfully and refund will be intiated in 7 working days";
    }
    		
}
