package com.wipro.simplyfly.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.wipro.simplyfly.repository.FlightOwnerRepository;
import com.wipro.simplyfly.repository.RouteRepository;
import com.wipro.simplyfly.repository.TransactionRepository;
import com.wipro.simplyfly.repository.UserRepository;
import com.wipro.simplyfly.service.IAdminService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

	@Autowired
	IAdminService service;
	@Autowired
	UserRepository userRepo;

	@Autowired
	RouteRepository routeRepo;

	@Autowired
	TransactionRepository transactionRepo;
	
	@Autowired
	FlightOwnerRepository ownerRepo;
	
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
	    String result = service.deleteUser(userId);
	    
	    if ("HAS_ACTIVE_BOOKINGS".equals(result)) {
	        return ResponseEntity.status(HttpStatus.CONFLICT)
	            .body("Cannot delete user: This user has existing flight bookings.");
	    }
	    
	    return ResponseEntity.ok("User and associated account deleted successfully.");
	}

	@GetMapping("/owners")
	public List<FlightOwnerDTO> manageFlightOwners() {
		return service.manageFlightOwners();
	}
	
	@GetMapping("/owners/{id}/inventory")
	public ResponseEntity<?> getOwnerInventory(@PathVariable Long id) {
	    try {
	        List<Map<String, Object>> inventory = service.getOwnerInventory(id);
	        return ResponseEntity.ok(inventory);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("Error fetching inventory: " + e.getMessage());
	    }
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
	public ResponseEntity<?> addRoute(@RequestBody RouteDTO routeDTO) {
	    try {
	        RouteDTO saved = service.addRoute(routeDTO);
	        return ResponseEntity.ok(saved);
	    } catch (RuntimeException e) {
	        return ResponseEntity.badRequest().body(e.getMessage());
	    }
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
	public void cancelBooking(@PathVariable Long bookingId) {
		 service.cancelBooking(bookingId);
	}
	
	@GetMapping("/dashboard-stats")
	public ResponseEntity<Map<String, Object>> getDashboardStats() {
	    Map<String, Object> stats = new HashMap<>();
	    stats.put("totalRoutes", routeRepo.count());
	    stats.put("totalUsers", userRepo.count());
	    stats.put("totalOwners", ownerRepo.count());
	    
	    
	    return ResponseEntity.ok(stats);
	}
}