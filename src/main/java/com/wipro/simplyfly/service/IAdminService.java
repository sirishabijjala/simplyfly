package com.wipro.simplyfly.service;

import java.util.List;

import com.wipro.simplyfly.dto.BookingResponseDTO;
import com.wipro.simplyfly.dto.FlightOwnerDTO;
import com.wipro.simplyfly.dto.RegisterRequest;
import com.wipro.simplyfly.dto.RouteDTO;
import com.wipro.simplyfly.dto.UserDTO;

public interface IAdminService {
	List<UserDTO> manageUsers();
	String addUser(RegisterRequest request);
	String updateUser(Long userId, RegisterRequest request);
	boolean deleteUser(Long userId);

	List<FlightOwnerDTO> manageFlightOwners();
	String addFlightOwner(RegisterRequest request);
	String updateFlightOwner(Long ownerId, RegisterRequest request);
	boolean deleteFlightOwner(Long ownerId);

	List<RouteDTO> manageRoutes();
	RouteDTO addRoute(RouteDTO routeDTO);
	RouteDTO updateRoute(int routeId, RouteDTO routeDTO);
	boolean deleteRoute(int routeId);

	List<BookingResponseDTO> manageBookings();
	boolean cancelBooking(Long bookingId);
}