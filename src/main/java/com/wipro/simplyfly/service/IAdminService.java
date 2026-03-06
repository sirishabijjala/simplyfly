package com.wipro.simplyfly.service;

import java.util.List;
import java.util.Map;

import com.wipro.simplyfly.dto.BookingResponseDTO;
import com.wipro.simplyfly.dto.FlightOwnerDTO;
import com.wipro.simplyfly.dto.RegisterRequest;
import com.wipro.simplyfly.dto.RouteDTO;
import com.wipro.simplyfly.dto.UserDTO;

public interface IAdminService {
	List<UserDTO> manageUsers();
	String addUser(RegisterRequest request);
	String updateUser(Long userId, RegisterRequest request);
	String deleteUser(Long userId);

	List<FlightOwnerDTO> manageFlightOwners();
	public List<Map<String, Object>> getOwnerInventory(Long ownerId);
	String addFlightOwner(RegisterRequest request);
	String updateFlightOwner(Long ownerId, RegisterRequest request);
	String deleteFlightOwner(Long ownerId);

	List<RouteDTO> manageRoutes();
	RouteDTO addRoute(RouteDTO routeDTO);
	RouteDTO updateRoute(int routeId, RouteDTO routeDTO);
	String deleteRoute(int routeId);

	List<BookingResponseDTO> manageBookings();
	void cancelBooking(Long bookingId);
}