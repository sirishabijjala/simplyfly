package com.wipro.simplyfly.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import com.wipro.simplyfly.exceptions.BookingNotFoundException;
import com.wipro.simplyfly.exceptions.EmailAlreadyExistsException;
import com.wipro.simplyfly.exceptions.FlightOwnerNotFoundException;
import com.wipro.simplyfly.exceptions.RouteNotFoundException;
import com.wipro.simplyfly.exceptions.UserNotFoundException;
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
	public boolean deleteUser(Long userId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

		Account account = user.getAccount();

		if (account != null) {
			accountRepo.delete(account);
		}

		userRepo.delete(user);

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
		Route route = routeRepo.findById(routeId).orElseThrow(() -> new RouteNotFoundException(routeId));

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

			if (b.getUser() != null) {
				d.setUserName(b.getUser().getName());
			} else {
				d.setUserName("N/A");
			}

			if (b.getSchedule() != null && b.getSchedule().getFlight() != null) {

				d.setFlightName(b.getSchedule().getFlight().getFlightName());

				if (b.getSchedule().getFlight().getRoute() != null) {
					d.setOrigin(b.getSchedule().getFlight().getRoute().getSource());
					d.setDestination(b.getSchedule().getFlight().getRoute().getDestination());
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
		Booking b = bookingRepo.findById(bookingId)
				.orElseThrow(() -> new BookingNotFoundException("Booking not found with id: " + bookingId));
		;
		if (b != null) {
			b.setBookingStatus("CANCELLED");
			bookingRepo.save(b);
			return true;
		}
		return false;
	}
}
