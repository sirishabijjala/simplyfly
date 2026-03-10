package com.wipro.simplyfly.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.wipro.simplyfly.dto.FlightOwnerDTO;
import com.wipro.simplyfly.dto.RegisterRequest;
import com.wipro.simplyfly.dto.RouteDTO;
import com.wipro.simplyfly.dto.UserDTO;
import com.wipro.simplyfly.entity.Account;
import com.wipro.simplyfly.entity.Booking;
import com.wipro.simplyfly.entity.FlightOwner;
import com.wipro.simplyfly.entity.Route;
import com.wipro.simplyfly.entity.User;
import com.wipro.simplyfly.repository.AccountRepository;
import com.wipro.simplyfly.repository.BookingRepository;
import com.wipro.simplyfly.repository.FlightOwnerRepository;
import com.wipro.simplyfly.repository.RouteRepository;
import com.wipro.simplyfly.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class AdminServiceImpTest {
	
    @Mock
    private UserRepository userRepo;

    @Mock
    private FlightOwnerRepository ownerRepo;

    @Mock
    private RouteRepository routeRepo;

    @Mock
    private BookingRepository bookingRepo;

    @Mock
    private AccountRepository accountRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AdminServiceImp adminService;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	 @Test
	    void testManageUsers() {
	        User user = new User();
	        user.setId(1L);
	        user.setName("Kaavya");

	        when(userRepo.findAll()).thenReturn(List.of(user));

	        List<UserDTO> result = adminService.manageUsers();

	        assertEquals(1, result.size());
	        assertEquals("Kaavya", result.get(0).getName());
	    }


	 @Test
	    void testAddUser() {
	        RegisterRequest req = new RegisterRequest();
	        req.setName("Kaavya");
	        req.setEmail("kaavya@gmail.com");
	        req.setPassword("123");

	        when(accountRepo.findByEmail("kaavya@gmail.com")).thenReturn(Optional.empty());
	        when(passwordEncoder.encode("123")).thenReturn("encoded");

	        String result = adminService.addUser(req);

	        assertEquals("User added successfully", result);
	        verify(userRepo).save(any(User.class));
	        verify(accountRepo).save(any(Account.class));
	    }

	    @Test
	    @Disabled
	    void testUpdateUser() {
	    }

	    @Test
	    void testDeleteUser() {
	        User user = new User();
	        user.setId(1L);
	        user.setAccount(new Account());

	        when(userRepo.findById(1L)).thenReturn(Optional.of(user));

	        String result = adminService.deleteUser(1L);

	        verify(userRepo).delete(user);
	    }

	    @Test
	    void testManageFlightOwners() {
	        FlightOwner owner = new FlightOwner();
	        owner.setId(1L);
	        owner.setName("owner");

	        when(ownerRepo.findAll()).thenReturn(List.of(owner));

	        List<FlightOwnerDTO> result = adminService.manageFlightOwners();

	        assertEquals(1, result.size());
	    }

	    @Test
	    void testAddFlightOwner() {
	        RegisterRequest req = new RegisterRequest();
	        req.setEmail("owner@test.com");
	        req.setPassword("123");

	        when(accountRepo.findByEmail("owner@test.com")).thenReturn(Optional.empty());
	        when(passwordEncoder.encode("123")).thenReturn("encoded");

	        String result = adminService.addFlightOwner(req);

	        assertEquals("FlightOwner added successfully", result);
	    }

	    @Test
	    void testUpdateFlightOwner() {
	        FlightOwner owner = new FlightOwner();
	        owner.setAccount(new Account());

	        when(ownerRepo.findById(1L)).thenReturn(Optional.of(owner));

	        RegisterRequest req = new RegisterRequest();
	        req.setName("Updated");

	        String result = adminService.updateFlightOwner(1L, req);

	        assertEquals("FlightOwner updated successfully", result);
	    }

	    @Test
	    @Disabled
	    void testDeleteFlightOwner() {
	        
	    }

	    @Test
	    void testManageRoutes() {
	        Route route = new Route();
	        route.setId(1);

	        when(routeRepo.findAll()).thenReturn(List.of(route));

	        List<RouteDTO> result = adminService.manageRoutes();

	        assertEquals(1, result.size());
	    }

	    @Test
	    void testAddRoute() {
	        RouteDTO routeDTO = new RouteDTO();
	        routeDTO.setSource("Chennai");
	        routeDTO.setDestination("Delhi");
	        routeDTO.setDistance(2200.0);
	        routeDTO.setEstimatedDuration("3h 15m");

	        when(routeRepo.existsBySourceAndDestination("Chennai", "Delhi")).thenReturn(false);
	        
	        Route savedRoute = new Route();
	        savedRoute.setId(101);
	        when(routeRepo.save(any(Route.class))).thenReturn(savedRoute);

	        RouteDTO result = adminService.addRoute(routeDTO);

	        assertNotNull(result);
	        assertEquals(101, result.getId());
	    }

	    @Test
	    @Disabled
	    void testUpdateRoute() {
	    }

	    @Test
	    @Disabled
	    void testDeleteRoute() {
	    }

	    @Test
	    @Disabled
	    void testManageBookings() {
	    }

	    @Test
	    @Disabled
	    void testCancelBooking() {
	        Booking booking = new Booking();
	        booking.setId(1L);

	        when(bookingRepo.findById(1L)).thenReturn(Optional.of(booking));

	        adminService.cancelBooking(1L);

	    }
}
