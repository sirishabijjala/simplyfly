package com.wipro.simplyfly.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wipro.simplyfly.dto.BookingResponseDTO;
import com.wipro.simplyfly.dto.FlightOwnerDTO;
import com.wipro.simplyfly.dto.RegisterRequest;
import com.wipro.simplyfly.dto.RouteDTO;
import com.wipro.simplyfly.dto.UserDTO;
import com.wipro.simplyfly.entity.Account;
import com.wipro.simplyfly.entity.Booking;
import com.wipro.simplyfly.entity.FlightOwner;
import com.wipro.simplyfly.entity.Route;
import com.wipro.simplyfly.entity.User;
import com.wipro.simplyfly.repository.AccountRepository;
import com.wipro.simplyfly.repository.BookingRepository;
import com.wipro.simplyfly.repository.FlightOwnerRepository;
import com.wipro.simplyfly.repository.RouteRepository;
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
	RouteRepository routeRepo;

	@Autowired
	BookingRepository bookingRepo;
	
	@Autowired
	AccountRepository accountRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public List<UserDTO> manageUsers() {
		List<User> users = userRepo.findAll();
		List<UserDTO> dtos = new ArrayList<>();
		for (User u : users) {
			UserDTO d = new UserDTO();
			d.setId(u.getId());
			d.setName(u.getName());
			d.setEmail(u.getEmail());
			d.setPhone(u.getPhone());
			d.setRole(u.getRole());
			d.setPassword(u.getPassword());
			d.setCreatedDate(u.getCreatedDate());
			dtos.add(d);
		}
		return dtos;
	}

    public String addUser(RegisterRequest request) {
        if (accountRepo.findByEmail(request.getEmail()).isPresent()) {
            return "Email already exists";
        }

        Account account = new Account();
        account.setName(request.getName());
        account.setEmail(request.getEmail());
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        account.setRole("USER");
        account.setActive(true);
        accountRepo.save(account);

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setRole("USER");
        user.setEnabled(true);
        user.setCreatedDate(LocalDateTime.now());
        user.setAccount(account); 
        userRepo.save(user);

        return "User added successfully";
    }

    public String updateUser(Long userId, RegisterRequest request) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update User fields
        user.setName(request.getName() != null ? request.getName() : user.getName());
        user.setPhone(request.getPhone() != null ? request.getPhone() : user.getPhone());
        user.setRole(request.getRole() != null ? request.getRole() : user.getRole());

        // Update linked Account if needed
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
	public boolean deleteUser(Long userId) {
		userRepo.deleteById(userId);
		return true;
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
			dtos.add(d);
		}
		return dtos;
	}

	public String addFlightOwner(RegisterRequest request) {
        if (accountRepo.findByEmail(request.getEmail()).isPresent()) {
            return "Email already exists";
        }

        // Create Account
        Account account = new Account();
        account.setName(request.getName());
        account.setEmail(request.getEmail());
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        account.setRole("OWNER");
        account.setActive(true);
        accountRepo.save(account);

        // Create FlightOwner
        FlightOwner owner = new FlightOwner();
        owner.setName(request.getName());
        owner.setEmail(request.getEmail());
        owner.setAccount(account); // Link
        ownerRepo.save(owner);

        return "FlightOwner added successfully";
    }

	public String updateFlightOwner(Long ownerId, RegisterRequest request) {
	    FlightOwner owner = ownerRepo.findById(ownerId)
	            .orElseThrow(() -> new RuntimeException("FlightOwner not found"));

	    // Update FlightOwner fields
	    owner.setName(request.getName() != null ? request.getName() : owner.getName());
	    owner.setEmail(request.getEmail() != null ? request.getEmail() : owner.getEmail()); // update FlightOwner email too

	    // Update linked Account
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

	public boolean deleteFlightOwner(Long ownerId) {
	    FlightOwner owner = ownerRepo.findById(ownerId)
	            .orElseThrow(() -> new RuntimeException("FlightOwner not found"));

	    Account account = owner.getAccount();
	    if (account != null) {
	        accountRepo.delete(account);
	    }

	    return true;
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
		Route r = routeRepo.findById(routeId).orElse(null);
		if (r != null) {
			r.setId(routeDTO.getId());
			r.setSource(routeDTO.getSource());
			r.setDestination(routeDTO.getDestination());
			r.setDistance(routeDTO.getDistance());
			r.setEstimatedDuration(routeDTO.getEstimatedDuration());
			routeRepo.save(r);
			return routeDTO;
		}
		return null;
	}

	@Override
	public boolean deleteRoute(int routeId) {
		routeRepo.deleteById(routeId);
		return true;
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

			if (b.getSchedule() != null) {
				if (b.getSchedule().getFlight() != null) {
					d.setFlightName(b.getSchedule().getFlight().getFlightName());

					if (b.getSchedule().getFlight().getRoute() != null) {
						d.setOrigin(b.getSchedule().getFlight().getRoute().getSource());
						d.setDestination(b.getSchedule().getFlight().getRoute().getDestination());
					}
				}
			} else {
				d.setFlightName("N/A");
				d.setOrigin("N/A");
				d.setDestination("N/A");
			}

			dtos.add(d);
		}
		return dtos;
	}

	@Override
	public boolean cancelBooking(Long bookingId) {
		Booking b = bookingRepo.findById(bookingId).orElse(null);
		if (b != null) {
			b.setBookingStatus("CANCELLED");
			bookingRepo.save(b);
			return true;
		}
		return false;
	}
}
