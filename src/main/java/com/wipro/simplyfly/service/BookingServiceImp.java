package com.wipro.simplyfly.service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.wipro.simplyfly.dto.BookingRequestDTO;
import com.wipro.simplyfly.dto.BookingResponseDTO;
import com.wipro.simplyfly.dto.PassengerRequestDTO;
import com.wipro.simplyfly.dto.PassengerResponseDTO;
import com.wipro.simplyfly.dto.TransactionRequestDTO;
import com.wipro.simplyfly.entity.Booking;
import com.wipro.simplyfly.entity.Passenger;
import com.wipro.simplyfly.entity.Schedule;
import com.wipro.simplyfly.entity.Seat;
import com.wipro.simplyfly.entity.User;
import com.wipro.simplyfly.repository.BookingRepository;
import com.wipro.simplyfly.repository.PassengerRepository;
import com.wipro.simplyfly.repository.ScheduleRepository;
import com.wipro.simplyfly.repository.SeatRepository;
import com.wipro.simplyfly.repository.UserRepository;

import jakarta.transaction.Transactional;
@Service
public class BookingServiceImp implements IBookingService{
	@Autowired
	BookingRepository bookingRepository; 
  
    @Autowired
    UserRepository userRepository;
    @Autowired
    PassengerRepository passengerRepository;
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    ITransactionService transactionService;
    @Autowired
    SeatRepository seatRepository;
   
    @Override
    @Transactional
    public BookingResponseDTO createBooking(BookingRequestDTO requestDTO) {

        // 1️⃣ Get Logged-in User
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2️⃣ Get Schedule
        Schedule schedule = scheduleRepository.findById(requestDTO.getScheduleId())
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
       
        
          //  double totalAmount = 0
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setSchedule(schedule);
        booking.setBookingDate(LocalDateTime.now());
        booking.setBookingStatus("CONFIRMED");
        booking.setBookingReference(UUID.randomUUID().toString());
        booking.setNumberOfSeats(requestDTO.getPassengers().size());
     

        booking = bookingRepository.save(booking);

       ;
        List<Passenger> passengerList = new ArrayList<>();
        double totalAmount = 0;

        for (PassengerRequestDTO passengerDTO : requestDTO.getPassengers()) {

        	Seat seat = seatRepository
        	        .findSeatForUpdate(
        	                passengerDTO.getSeatNumber(),
        	                requestDTO.getScheduleId()
        	        )
        	        .orElseThrow(() -> new RuntimeException("Seat not found"));

            if (!seat.isAvailable()) {
                throw new RuntimeException("Seat " + seat.getSeatNumber() + " already booked");
            }

            double seatPrice = schedule.getFare();

            if (passengerDTO.getAge() < 12) {
                seatPrice = seatPrice * 0.5;
            }

            totalAmount += seatPrice;

            seat.setAvailable(false);
            seatRepository.save(seat);

            Passenger passenger = new Passenger();
            passenger.setName(passengerDTO.getName());
            passenger.setAge(passengerDTO.getAge());
            passenger.setGender(passengerDTO.getGender());
            passenger.setSeat(seat);
            passenger.setBooking(booking);

            passengerList.add(passenger);
        }
           
         

        passengerRepository.saveAll(passengerList);
  
        booking.setPassengers(passengerList);
     
        // 8️⃣ Reduce Available Seats
        schedule.setAvailableSeats(
                schedule.getAvailableSeats() - requestDTO.getPassengers().size()
        );
        scheduleRepository.save(schedule);

        // 9️⃣ Update Total Amount
     booking.setTotalAmount(totalAmount);
        bookingRepository.save(booking);

        // 🔟 Call Transaction Service
        TransactionRequestDTO transactionDTO = new TransactionRequestDTO();
        transactionDTO.setPaymentMethod(requestDTO.getPaymentMethod());

        transactionService.makePayment(booking.getId(), transactionDTO);
    

        return mapToResponse(booking);
    }

	@Override
	@Transactional
	public BookingResponseDTO getBooking(Long bookingId) {
		Booking response=bookingRepository.findById(bookingId).orElseThrow(()->new RuntimeException("Booking Not Found"));
		return mapToResponse(response);
	}

	@Override
	public List<BookingResponseDTO> getAllBookings() {
		
		return  bookingRepository.findAll().stream().map(this::mapToResponse).toList();
	}
	
	@Override
	public List<BookingResponseDTO> getBookingsByLoggedInUser() {
	    // Get the email from the Security Context (JWT Token)
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String username = authentication.getName();

	    // Find bookings belonging to this user
	    return bookingRepository.findByUserEmail(username)
	            .stream()
	            .map(this::mapToResponse)
	            .toList();
	}
	@Override
	@Transactional
	public void cancelBooking(Long bookingId) {

	    Booking booking = bookingRepository.findById(bookingId)
	            .orElseThrow(() -> new RuntimeException("Booking not found"));

	    if ("CANCELLED".equals(booking.getBookingStatus())) {
	        throw new RuntimeException("Booking already cancelled");
	    }

	    // 🔐 Security Check
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String username = authentication.getName();

	    if (!booking.getUser().getEmail().equals(username)) {
	        throw new RuntimeException("Not authorized to cancel this booking");
	    }

	    // Release seats
	    for (Passenger passenger : booking.getPassengers()) {
	        Seat seat = passenger.getSeat();
	        seat.setAvailable(true);
	        seatRepository.save(seat);
	    }

	    // Increase available seats
	    Schedule schedule = booking.getSchedule();
	    schedule.setAvailableSeats(
	            schedule.getAvailableSeats() + booking.getPassengers().size()
	    );
	    scheduleRepository.save(schedule);

	    booking.setBookingStatus("CANCELLED");
	    bookingRepository.save(booking);

	    // Refund
	    transactionService.refundPayment(bookingId);
	   
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
		response.setOrigin(booking.getSchedule().getFlight().getRoute().getSource());
		response.setDestination(booking.getSchedule().getFlight().getRoute().getDestination());
		
		if (booking.getSchedule() != null && booking.getSchedule().getFlight() != null) {
	        String airline = booking.getSchedule().getFlight().getFlightOwner().getName();
	        String flightNo = booking.getSchedule().getFlight().getFlightNumber();
	        String model = booking.getSchedule().getFlight().getFlightName();

	        response.setFlightName(airline + " | " + flightNo + " (" + model + ")");
	        
	        response.setOrigin(booking.getSchedule().getFlight().getRoute().getSource());
	        response.setDestination(booking.getSchedule().getFlight().getRoute().getDestination());
	    }
	    List<PassengerResponseDTO> passengerResponses = booking.getPassengers()
	            .stream()
	            .map(passenger -> {
	                PassengerResponseDTO p = new PassengerResponseDTO();
	                p.setName(passenger.getName());
	                p.setAge(passenger.getAge());
	                p.setGender(passenger.getGender());
	                p.setSeatId(passenger.getSeat().getSeatNumber());
	                return p;
	            })
	            .toList();

	    response.setPassengers(passengerResponses);



		
		return response;
	}

}