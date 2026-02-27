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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDTO addUser(UserDTO userDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDTO updateUser(Long userId, UserDTO userDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteUser(Long userId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<FlightOwnerDTO> manageFlightOwners() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FlightOwnerDTO addFlightOwner(FlightOwnerDTO ownerDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FlightOwnerDTO updateFlightOwner(Long ownerId, FlightOwnerDTO ownerDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteFlightOwner(Long ownerId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<RouteDTO> manageRoutes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RouteDTO addRoute(RouteDTO routeDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RouteDTO updateRoute(int routeId, RouteDTO routeDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteRoute(int routeId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<BookingResponseDTO> manageBookings() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean cancelBooking(Long bookingId) {
		// TODO Auto-generated method stub
		return false;
	}


}