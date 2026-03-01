package com.wipro.simplyfly.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.simplyfly.dto.BookingResponseDTO;
import com.wipro.simplyfly.dto.FlightOwnerDTO;
import com.wipro.simplyfly.dto.RouteDTO;
import com.wipro.simplyfly.dto.UserDTO;
import com.wipro.simplyfly.entity.Booking;
import com.wipro.simplyfly.entity.FlightOwner;
import com.wipro.simplyfly.entity.Route;
import com.wipro.simplyfly.entity.User;
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
			dtos.add(d);
		}
		return dtos;
	}

	@Override
	public UserDTO addUser(UserDTO userDTO) {
		User u = new User();
		u.setName(userDTO.getName());
		u.setEmail(userDTO.getEmail());
		u.setPassword(userDTO.getPassword());
		u.setPhone(userDTO.getPhone());
		u.setRole(userDTO.getRole());

		User saved = userRepo.save(u);
		userDTO.setId(saved.getId());
		return userDTO;
	}

	@Override
	public UserDTO updateUser(Long userId, UserDTO userDTO) {
		User u = userRepo.findById(userId).orElse(null);
		if (u != null) {
			u.setName(userDTO.getName());
			u.setPhone(userDTO.getPhone());
			userRepo.save(u);
			return userDTO;
		}
		return null;
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

	@Override
	public FlightOwnerDTO addFlightOwner(FlightOwnerDTO ownerDTO) {
		FlightOwner o = new FlightOwner();
		o.setName(ownerDTO.getName());
		o.setEmail(ownerDTO.getEmail());

		FlightOwner saved = ownerRepo.save(o);
		ownerDTO.setId(saved.getId());
		return ownerDTO;
	}

	@Override
	public FlightOwnerDTO updateFlightOwner(Long ownerId, FlightOwnerDTO ownerDTO) {
		FlightOwner o = ownerRepo.findById(ownerId).orElse(null);
		if (o != null) {
			o.setName(ownerDTO.getName());
			ownerRepo.save(o);
			return ownerDTO;
		}
		return null;
	}

	@Override
	public boolean deleteFlightOwner(Long ownerId) {
		ownerRepo.deleteById(ownerId);
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
			d.setFlightName("N/A");
			d.setOrigin("N/A");
			d.setDestination("N/A");

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
