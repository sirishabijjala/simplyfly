package com.wipro.simplyfly.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import com.wipro.simplyfly.dto.RegisterRequest;
import com.wipro.simplyfly.dto.RouteDTO;
import com.wipro.simplyfly.dto.UserDTO;
import com.wipro.simplyfly.service.IAdminService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

	@Autowired
	IAdminService service;

	@GetMapping("/users")
	public List<UserDTO> manageUsers() {
		return service.manageUsers();
	}

	@PostMapping("/users")
	public ResponseEntity<String> addUser(@RequestBody RegisterRequest request) {
		String result = service.addUser(request);
		if (result.contains("exists")) {
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}

	@PutMapping("/users/{id}")
	public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody RegisterRequest request) {
		String result = service.updateUser(id, request);
		if (result.contains("exists"))
			return ResponseEntity.badRequest().body(result);
		return ResponseEntity.ok(result);
	}

	@DeleteMapping("/users/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
	    boolean deleted = service.deleteUser(userId);
	    return deleted ? ResponseEntity.ok("User deleted successfully") 
	                   : ResponseEntity.status(404).body("User not found");
	}

	@GetMapping("/owners")
	public List<FlightOwnerDTO> manageFlightOwners() {
		return service.manageFlightOwners();
	}

	@PostMapping("/owners")
	public ResponseEntity<String> addOwner(@RequestBody RegisterRequest request) {
		String result = service.addFlightOwner(request);
		if (result.contains("exists")) {
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}

	@PutMapping("/owners/{id}")
	public ResponseEntity<String> updateOwner(@PathVariable Long id,
	                                          @RequestBody RegisterRequest request) {
	    String result = service.updateFlightOwner(id, request);
	    if (result.contains("exists")) return ResponseEntity.badRequest().body(result);
	    return ResponseEntity.ok(result);
	}

	@DeleteMapping("/owners/{ownerId}")
	public String deleteFlightOwner(@PathVariable Long ownerId) {
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
	public String  deleteRoute(@PathVariable int routeId) {
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