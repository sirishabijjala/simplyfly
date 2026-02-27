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