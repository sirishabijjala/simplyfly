package com.wipro.simplyfly.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

import com.wipro.simplyfly.dto.AuthResponse;
import com.wipro.simplyfly.dto.LoginRequest;
import com.wipro.simplyfly.dto.RegisterRequest;
import com.wipro.simplyfly.repository.AccountRepository;

import jakarta.transaction.Transactional;
@SpringBootTest
@Transactional
class AccountServiceImpTest {
	
	@Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository repository;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testRegister() {
		RegisterRequest request = new RegisterRequest();
        request.setName("Sirisha");
        request.setEmail("sirisha@gmail.com");
        request.setPassword("1234");
        request.setRole("ROLE_USER");

        String response = accountService.register(request);

        assertEquals("User Registered Successfully", response);
        assertTrue(repository.findByEmail("sirisha@gmail.com").isPresent());
	}

	@Test
	void testLogin() {
		RegisterRequest request = new RegisterRequest();
        request.setName("Sirisha");
        request.setEmail("sirisha@gmail.com");
        request.setPassword("1234");
        request.setRole("ROLE_USER");

        accountService.register(request);

        // Now login
        LoginRequest login = new LoginRequest();
        login.setEmail("sirisha@gmail.com");
        login.setPassword("1234");

        AuthResponse response = accountService.login(login);

        assertNotNull(response);
        assertNotNull(response.getToken());
        assertEquals("Login Successful", response.getMessage());
	}

}
