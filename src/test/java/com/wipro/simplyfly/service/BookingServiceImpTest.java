package com.wipro.simplyfly.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wipro.simplyfly.entity.Booking;
import com.wipro.simplyfly.entity.Flight;
import com.wipro.simplyfly.entity.FlightOwner;
import com.wipro.simplyfly.entity.Passenger;
import com.wipro.simplyfly.entity.Route;
import com.wipro.simplyfly.entity.Schedule;
import com.wipro.simplyfly.entity.User;
import com.wipro.simplyfly.repository.BookingRepository;
import com.wipro.simplyfly.repository.PassengerRepository;
import com.wipro.simplyfly.repository.ScheduleRepository;
import com.wipro.simplyfly.repository.SeatRepository;
import com.wipro.simplyfly.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class BookingServiceImpTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PassengerRepository passengerRepository;

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private ITransactionService transactionService;

    @InjectMocks
    private BookingServiceImp bookingService;

    private Booking booking;

    @BeforeEach
    void setUp() {

        User user = new User();
        user.setId(1L);
        user.setName("Sai");
        user.setEmail("sai@test.com");

        Route route = new Route();
        route.setSource("Hyderabad");
        route.setDestination("Delhi");

        FlightOwner owner = new FlightOwner();
        owner.setId(1L);
        owner.setName("Air India");

        Flight flight = new Flight();
        flight.setFlightName("Boeing 737");
        flight.setFlightNumber("AI101");
        flight.setRoute(route);
        flight.setFlightOwner(owner);

        Schedule schedule = new Schedule();
        schedule.setId(3L);
        schedule.setFare(2500);
        schedule.setFlight(flight);

        booking = new Booking();
        booking.setId(3L);
        booking.setUser(user);
        booking.setSchedule(schedule);
        booking.setBookingDate(LocalDateTime.now());
        booking.setNumberOfSeats(1);
        booking.setTotalAmount(2500);
        booking.setBookingStatus("CONFIRMED");

        // ⭐ ADD PASSENGER LIST
        Passenger passenger = new Passenger();
        passenger.setName("Ravi");
        passenger.setAge(25);
        passenger.setGender("Male");
        passenger.setBooking(booking);

        booking.setPassengers(List.of(passenger));  // IMPORTANT FIX

        when(bookingRepository.findById(3L)).thenReturn(Optional.of(booking));




      
    }

    @Test
    void testGetBooking() {

        var result = bookingService.getBooking(3L);

        assertNotNull(result);
        assertEquals(1, result.getNumberOfSeats());
        assertEquals(2500, result.getTotalAmount());
        assertEquals("Sai", result.getUserName());
    }

}
