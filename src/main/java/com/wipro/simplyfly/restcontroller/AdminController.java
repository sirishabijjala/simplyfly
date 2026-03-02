package com.wipro.simplyfly.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.simplyfly.dto.BookingResponseDTO;
import com.wipro.simplyfly.dto.FlightOwnerDTO;
import com.wipro.simplyfly.dto.RouteDTO;
import com.wipro.simplyfly.dto.UserDTO;
import com.wipro.simplyfly.service.IAdminService;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('Admin')")
public class AdminController {

	@Autowired
	IAdminService service;


	@GetMapping("/users")
	public List<UserDTO> manageUsers() {
		return service.manageUsers();
	}

	@PostMapping("/users")
	public UserDTO addUser(@RequestBody UserDTO userDTO) {
		return service.addUser(userDTO);
	}

	@PutMapping("/users/{userId}")
	public UserDTO updateUser(@PathVariable Long userId, @RequestBody UserDTO userDTO) {
		return service.updateUser(userId, userDTO);
	}

	@DeleteMapping("/users/{userId}")
	public boolean deleteUser(@PathVariable Long userId) {
		return service.deleteUser(userId);
	}


	@GetMapping("/owners")
	public List<FlightOwnerDTO> manageFlightOwners() {
		return service.manageFlightOwners();
	}

	@PostMapping("/owners")
	public FlightOwnerDTO addFlightOwner(@RequestBody FlightOwnerDTO ownerDTO) {
		return service.addFlightOwner(ownerDTO);
	}

	@PutMapping("/owners/{ownerId}")
	public FlightOwnerDTO updateFlightOwner(@PathVariable Long ownerId, @RequestBody FlightOwnerDTO ownerDTO) {
		return service.updateFlightOwner(ownerId, ownerDTO);
	}

	@DeleteMapping("/owners/{ownerId}")
	public boolean deleteFlightOwner(@PathVariable Long ownerId) {
		return service.deleteFlightOwner(ownerId);
	}


	@GetMapping("/routes")
	public List<RouteDTO> manageRoutes() {
		return service.manageRoutes();
	}

	@PostMapping("/routes")
	public RouteDTO addRoute(@RequestBody RouteDTO routeDTO) {
		return service.addRoute(routeDTO);
	}

	@PutMapping("/routes/{routeId}")
	public RouteDTO updateRoute(@PathVariable int routeId, @RequestBody RouteDTO routeDTO) {
		return service.updateRoute(routeId, routeDTO);
	}

	@DeleteMapping("/routes/{routeId}")
	public boolean deleteRoute(@PathVariable int routeId) {
		return service.deleteRoute(routeId);
	}


	@GetMapping("/bookings")
	public List<BookingResponseDTO> manageBookings() {
		return service.manageBookings();
	}

	@PutMapping("/bookings/cancel/{bookingId}")
	public boolean cancelBooking(@PathVariable Long bookingId) {
		return service.cancelBooking(bookingId);
	}
}