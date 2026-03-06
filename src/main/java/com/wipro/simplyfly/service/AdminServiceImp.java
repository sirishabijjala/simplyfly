package com.wipro.simplyfly.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wipro.simplyfly.dto.BookingResponseDTO;
import com.wipro.simplyfly.dto.FlightOwnerDTO;
import com.wipro.simplyfly.dto.PassengerResponseDTO;
import com.wipro.simplyfly.dto.RegisterRequest;
import com.wipro.simplyfly.dto.RouteDTO;
import com.wipro.simplyfly.dto.UserDTO;
import com.wipro.simplyfly.entity.Account;
import com.wipro.simplyfly.entity.Booking;
import com.wipro.simplyfly.entity.Flight;
import com.wipro.simplyfly.entity.FlightOwner;
import com.wipro.simplyfly.entity.Passenger;
import com.wipro.simplyfly.entity.Route;
import com.wipro.simplyfly.entity.Schedule;
import com.wipro.simplyfly.entity.Seat;
import com.wipro.simplyfly.entity.User;
import com.wipro.simplyfly.exceptions.EmailAlreadyExistsException;
import com.wipro.simplyfly.exceptions.FlightOwnerNotFoundException;
import com.wipro.simplyfly.exceptions.RouteNotFoundException;
import com.wipro.simplyfly.exceptions.UserNotFoundException;
import com.wipro.simplyfly.repository.AccountRepository;
import com.wipro.simplyfly.repository.BookingRepository;
import com.wipro.simplyfly.repository.FlightOwnerRepository;
import com.wipro.simplyfly.repository.FlightRepository;
import com.wipro.simplyfly.repository.RouteRepository;
import com.wipro.simplyfly.repository.ScheduleRepository;
import com.wipro.simplyfly.repository.SeatRepository;
import com.wipro.simplyfly.repository.UserRepository;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class AdminServiceImp implements IAdminService {

	@Autowired
	UserRepository userRepo;

	@Autowired
	FlightOwnerRepository ownerRepo;
	
	@Autowired
	FlightRepository flightRepo;

	@Autowired
	RouteRepository routeRepo;

	@Autowired
	BookingRepository bookingRepo;

	@Autowired
	AccountRepository accountRepo;
	
	@Autowired
    ScheduleRepository scheduleRepo;
	
    @Autowired
    SeatRepository seatRepo;
	@Autowired
	
	private PasswordEncoder passwordEncoder;
	@Autowired
	private ITransactionService transactionService;;

	@Override
	public List<UserDTO> manageUsers() {
		return userRepo.findAll().stream().map(u -> {
			UserDTO dto = new UserDTO();
			dto.setId(u.getId());
			dto.setName(u.getName());
			dto.setEmail(u.getEmail());
			dto.setPhone(u.getPhone());
			dto.setAddress(u.getAddress());
			dto.setGender(u.getGender());
			dto.setDateOfBirth(u.getDateOfBirth());
			return dto;
		}).collect(Collectors.toList());
	}

	@Override
	public String addUser(RegisterRequest request) {
		if (accountRepo.findByEmail(request.getEmail()).isPresent()) {
			throw new EmailAlreadyExistsException("Email already exists: " + request.getEmail());
		}

		// Create Account
		Account account = new Account();
		account.setName(request.getName());
		account.setEmail(request.getEmail());
		account.setPassword(passwordEncoder.encode(request.getPassword()));
		account.setRole("USER");
		account.setActive(true);
		accountRepo.save(account);

		// Create User
		User user = new User();
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setPhone(request.getPhone());
		user.setAddress(request.getAddress());
		user.setGender(request.getGender());
		user.setDateOfBirth(request.getDateOfBirth());
		user.setAccount(account);
		userRepo.save(user);

		return "User added successfully";
	}

	@Override
	public String updateUser(Long userId, RegisterRequest request) {
		User user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

		user.setName(request.getName() != null ? request.getName() : user.getName());
		user.setEmail(request.getEmail() != null ? request.getEmail() : user.getEmail());
		user.setPassword(
				request.getPassword() != null ? passwordEncoder.encode(request.getPassword()) : user.getPassword());
		user.setPhone(request.getPhone() != null ? request.getPhone() : user.getPhone());
		user.setAddress(request.getAddress() != null ? request.getAddress() : user.getAddress());
		user.setGender(request.getGender() != null ? request.getGender() : user.getGender());
		user.setDateOfBirth(request.getDateOfBirth() != null ? request.getDateOfBirth() : user.getDateOfBirth());

		Account account = user.getAccount();
		if (request.getEmail() != null && !request.getEmail().equals(account.getEmail())) {
			if (accountRepo.findByEmail(request.getEmail()).isPresent()) {
				return "Email already exists";
			}
			account.setEmail(request.getEmail());
		}
		if (request.getPassword() != null && !request.getPassword().isEmpty()) {
			account.setPassword(passwordEncoder.encode(request.getPassword()));
		}
		if (request.getName() != null) {
			account.setName(request.getName());
		}

		accountRepo.save(account);
		userRepo.save(user);

		return "User updated successfully";
	}

	@Override
	public String deleteUser(Long userId) {
        User user = userRepo.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(userId));

        // 1. Check if the user is linked to any bookings
        // This works even without a List<Booking> in the User class
        if (bookingRepo.existsByUser_Id(userId)) {
            return "HAS_ACTIVE_BOOKINGS";
        }

        // 2. Delete the Account (Security/Credentials)
        Account account = user.getAccount();
        if (account != null) {
            accountRepo.delete(account);
        }

        // 3. Delete the User
        userRepo.delete(user);
        return "SUCCESS";
    }

	@Override
	public List<FlightOwnerDTO> manageFlightOwners() {
	    List<FlightOwner> owners = ownerRepo.findAll();
	    List<FlightOwnerDTO> dtos = new ArrayList<>();
	    for (FlightOwner o : owners) {
	        FlightOwnerDTO d = new FlightOwnerDTO();
	        d.setId(o.getId());
	        d.setName(o.getName());
	        d.setEmail(o.getEmail());
	        
	        // Populate counts
	        d.setFlightCount(o.getFlights() != null ? o.getFlights().size() : 0);
	        
	        int totalSchedules = 0;
	        if (o.getFlights() != null) {
	            for (Flight f : o.getFlights()) {
	                totalSchedules += (f.getSchedules() != null ? f.getSchedules().size() : 0);
	            }
	        }
	        d.setScheduleCount(totalSchedules);
	        
	        dtos.add(d);
	    }
	    return dtos;
	}

	@Override
	public String addFlightOwner(RegisterRequest request) {
		if (accountRepo.findByEmail(request.getEmail()).isPresent()) {
			throw new EmailAlreadyExistsException("Email already exists: " + request.getEmail());
		}

		Account account = new Account();
		account.setName(request.getName());
		account.setEmail(request.getEmail());
		account.setPassword(passwordEncoder.encode(request.getPassword()));
		account.setRole("OWNER");
		account.setActive(true);
		accountRepo.save(account);

		FlightOwner owner = new FlightOwner();
		owner.setName(request.getName());
		owner.setEmail(request.getEmail());
		owner.setAccount(account);
		ownerRepo.save(owner);

		return "FlightOwner added successfully";
	}
	
	@Override
	public List<Map<String, Object>> getOwnerInventory(Long ownerId) {
	    FlightOwner owner = ownerRepo.findById(ownerId)
	        .orElseThrow(() -> new FlightOwnerNotFoundException(ownerId));

	    List<Map<String, Object>> inventory = new ArrayList<>();

	    for (Flight f : owner.getFlights()) {
	        Map<String, Object> flightMap = new HashMap<>();
	        flightMap.put("flightNumber", f.getFlightNumber());
	        flightMap.put("model", f.getFlightName());
	        flightMap.put("route", f.getRoute().getSource() + " to " + f.getRoute().getDestination());
	        
	        // Get schedule details for this specific flight
	        List<Map<String, String>> schedules = f.getSchedules().stream().map(s -> {
	            Map<String, String> sMap = new HashMap<>();
	            sMap.put("departure", s.getDepartureTime().toString());
	            sMap.put("arrival", s.getArrivalTime().toString());
	            sMap.put("status", s.getAvailableSeats() > 0 ? "Open" : "Full");
	            return sMap;
	        }).toList();

	        flightMap.put("schedules", schedules);
	        inventory.add(flightMap);
	    }
	    return inventory;
	}

	@Override
	public String updateFlightOwner(Long ownerId, RegisterRequest request) {
		FlightOwner owner = ownerRepo.findById(ownerId).orElseThrow(() -> new FlightOwnerNotFoundException(ownerId));
		owner.setName(request.getName() != null ? request.getName() : owner.getName());
		owner.setEmail(request.getEmail() != null ? request.getEmail() : owner.getEmail());

		Account account = owner.getAccount();
		if (request.getEmail() != null && !request.getEmail().equals(account.getEmail())) {
			if (accountRepo.findByEmail(request.getEmail()).isPresent()) {
				return "Email already exists";
			}
			account.setEmail(request.getEmail());
		}
		if (request.getPassword() != null && !request.getPassword().isEmpty()) {
			account.setPassword(passwordEncoder.encode(request.getPassword()));
		}
		if (request.getName() != null) {
			account.setName(request.getName());
		}

		accountRepo.save(account);
		ownerRepo.save(owner);

		return "FlightOwner updated successfully";
	}

	@Override
	public String deleteFlightOwner(Long ownerId) {

		FlightOwner owner = ownerRepo.findById(ownerId).orElseThrow(() -> new FlightOwnerNotFoundException(ownerId));

		boolean hasBookings = bookingRepo.existsBySchedule_Flight_FlightOwner_Id(ownerId);

		if (hasBookings) {
			return "Flight owner cannot be deleted because flights under this owner have existing bookings.";
		}

		ownerRepo.delete(owner);
		accountRepo.delete(owner.getAccount());

		return "Flight owner deleted successfully ";
	}

	@Override
	public List<RouteDTO> manageRoutes() {
		List<Route> routes = routeRepo.findAll();
		List<RouteDTO> dtos = new ArrayList<>();
		for (Route r : routes) {
			RouteDTO d = new RouteDTO();
			d.setId(r.getId());
			d.setSource(r.getSource());
			d.setDestination(r.getDestination());
			d.setDistance(r.getDistance());
			d.setEstimatedDuration(r.getEstimatedDuration());
			dtos.add(d);
		}
		return dtos;
	}

	@Override
	public RouteDTO addRoute(RouteDTO routeDTO) {
	    if (routeRepo.existsBySourceAndDestination(routeDTO.getSource(), routeDTO.getDestination())) {
	        throw new RuntimeException("Route from " + routeDTO.getSource() + 
	                                   " to " + routeDTO.getDestination() + " already exists!");
	    }

	    Route r = new Route();
	    r.setSource(routeDTO.getSource());
	    r.setDestination(routeDTO.getDestination());
	    r.setDistance(routeDTO.getDistance());
	    r.setEstimatedDuration(routeDTO.getEstimatedDuration());

	    Route saved = routeRepo.save(r);
	    routeDTO.setId(saved.getId());
	    return routeDTO;
	}

	@Override
	public RouteDTO updateRoute(int routeId, RouteDTO routeDTO) {

		Route r = routeRepo.findById(routeId).orElseThrow(() -> new RouteNotFoundException(routeId));

		r.setSource(routeDTO.getSource());
		r.setDestination(routeDTO.getDestination());
		r.setDistance(routeDTO.getDistance());
		r.setEstimatedDuration(routeDTO.getEstimatedDuration());

		Route updatedRoute = routeRepo.save(r);

		RouteDTO dto = new RouteDTO();
		dto.setId(updatedRoute.getId());
		dto.setSource(updatedRoute.getSource());
		dto.setDestination(updatedRoute.getDestination());
		dto.setDistance(updatedRoute.getDistance());
		dto.setEstimatedDuration(updatedRoute.getEstimatedDuration());

		return dto;
	}

	@Override
	public String deleteRoute(int routeId) {
	    Route route = routeRepo.findById(routeId)
	            .orElseThrow(() -> new RouteNotFoundException(routeId));

	    if (flightRepo.existsByRoute(route)) {
	        return "Cannot delete route: There are active flights assigned to this route. Delete the flights first.";
	    }

	    routeRepo.delete(route);
	    return "Route deleted successfully";
	}

	@Override
	public List<BookingResponseDTO> manageBookings() {
	    List<Booking> bookings = bookingRepo.findAll();
	    List<BookingResponseDTO> dtos = new ArrayList<>();

	    for (Booking b : bookings) {
	        BookingResponseDTO d = new BookingResponseDTO();
	        
	        d.setBookingId(b.getId());
	        d.setBookingReference(b.getBookingReference());
	        d.setBookingStatus(b.getBookingStatus());
	        d.setBookingDate(b.getBookingDate());
	        d.setNumberOfSeats(b.getNumberOfSeats());
	        d.setTotalAmount(b.getTotalAmount());
	        d.setUserName(b.getUser() != null ? b.getUser().getName() : "N/A");

	        if (b.getSchedule() != null && b.getSchedule().getFlight() != null) {
	            var flight = b.getSchedule().getFlight();
	            var owner = flight.getFlightOwner();
	            
	            d.setFlightName("[" + owner.getName() + "] " + flight.getFlightNumber() + " (" + flight.getFlightName() + ")");
	            
	            if (flight.getRoute() != null) {
	                d.setOrigin(flight.getRoute().getSource());
	                d.setDestination(flight.getRoute().getDestination());
	            }
	        }

	        if (b.getPassengers() != null) {
	            List<PassengerResponseDTO> pDtos = b.getPassengers().stream().map(p -> {
	                PassengerResponseDTO pr = new PassengerResponseDTO();
	                pr.setName(p.getName());
	                pr.setAge(p.getAge());
	                pr.setGender(p.getGender());
	                pr.setSeatId(p.getSeat().getSeatNumber()); 
	                return pr;
	            }).toList();
	            d.setPassengers(pDtos);
	        }

	        dtos.add(d);
	    }
	    return dtos;
	}
	
	@Override
	@Transactional // Ensure this is present
	public void cancelBooking(Long bookingId) {
		    Booking booking = bookingRepo.findById(bookingId)
		            .orElseThrow(() -> new RuntimeException("Booking not found"));

		    if ("CANCELLED".equals(booking.getBookingStatus())) {
		        throw new RuntimeException("Booking already cancelled");
		    }

		    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		    String username = authentication.getName();
		    
		    boolean isAdmin = authentication.getAuthorities().stream()
		                        .anyMatch(a -> a.getAuthority().equals("ADMIN"));

		    if (!booking.getUser().getEmail().equals(username) && !isAdmin) {
		        throw new RuntimeException("Not authorized to cancel this booking");
		    }

		   
		    for (Passenger passenger : booking.getPassengers()) {
		        Seat seat = passenger.getSeat();
		        seat.setAvailable(true);
		        seatRepo.save(seat);
		    }

		    Schedule schedule = booking.getSchedule();
		    schedule.setAvailableSeats(
		            schedule.getAvailableSeats() + booking.getPassengers().size()
		    );
		    scheduleRepo.save(schedule);

		    booking.setBookingStatus("CANCELLED");
		    bookingRepo.save(booking);

		    transactionService.refundPayment(bookingId);
		}
}
