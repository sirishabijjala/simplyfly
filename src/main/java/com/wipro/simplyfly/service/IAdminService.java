package com.wipro.simplyfly.service;

import java.util.List;

import com.wipro.simplyfly.dto.BookingResponseDTO;
import com.wipro.simplyfly.dto.FlightOwnerDTO;
import com.wipro.simplyfly.dto.RouteDTO;
import com.wipro.simplyfly.dto.UserDTO;

public interface IAdminService {
	List<UserDTO> manageUsers();
	UserDTO addUser(UserDTO userDTO);
	UserDTO updateUser(Long userId, UserDTO userDTO);
	boolean deleteUser(Long userId);

	List<FlightOwnerDTO> manageFlightOwners();
	FlightOwnerDTO addFlightOwner(FlightOwnerDTO ownerDTO);
	FlightOwnerDTO updateFlightOwner(Long ownerId, FlightOwnerDTO ownerDTO);
	boolean deleteFlightOwner(Long ownerId);

	List<RouteDTO> manageRoutes();
	RouteDTO addRoute(RouteDTO routeDTO);
	RouteDTO updateRoute(int routeId, RouteDTO routeDTO);
	boolean deleteRoute(int routeId);

	List<BookingResponseDTO> manageBookings();
	boolean cancelBooking(Long bookingId);
}