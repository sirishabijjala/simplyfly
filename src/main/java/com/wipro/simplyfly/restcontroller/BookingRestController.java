package com.wipro.simplyfly.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/simplyfly/")
@PreAuthorize("hasAuthority('USER')")
public class BookingRestController {
	@Autowired
	IBookingService bookingService;
	//create booking
    // 1️⃣ Create Booking
    @PostMapping("BookTicket")
    public BookingResponseDTO createBooking(
            @Validated @RequestBody BookingRequestDTO bookingRequestDTO) {

        return bookingService.createBooking(bookingRequestDTO);
    }

    // 2️⃣ Get Booking By ID
    @GetMapping("GetBookingById/{bookingId}")
    public BookingResponseDTO getBookingById(@PathVariable Long bookingId) {

        return bookingService.getBooking(bookingId);
    }

    // 3️⃣ Get All Bookings
    @GetMapping("GetAll")
    public List<BookingResponseDTO> getAllBookings() {

        return bookingService.getAllBookings();
    }

    // 4️⃣ Cancel Booking
    @PutMapping("CancelBooking/{bookingId}")
    public String cancelBooking(@PathVariable Long bookingId) {

        bookingService.cancelBooking(bookingId);

        return "Booking cancelled successfully for ID "
                + bookingId
                + ". Refund will be initiated in 7 working days.";
    }

    		
}
