package com.wipro.simplyfly.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.simplyfly.dto.AccountResponse;
import com.wipro.simplyfly.dto.AuthResponse;
import com.wipro.simplyfly.dto.LoginRequest;
import com.wipro.simplyfly.dto.RegisterRequest;
import com.wipro.simplyfly.entity.Account;
import com.wipro.simplyfly.repository.AccountRepository;
import com.wipro.simplyfly.service.IAccountService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
    private IAccountService accountService;
	
	@Autowired
	private AccountRepository accountRepository;

    //  Register API
    @PostMapping("/register")
    public String register(@Valid @RequestBody RegisterRequest request) {
        return accountService.register(request);
    }

    //  Login API
    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return accountService.login(request);
    }
    
    @GetMapping("/profile")
    public AccountResponse getProfile(Authentication auth) {

        String email = auth.getName();

        Account account = accountRepository.findByEmail(email).get();

        AccountResponse response = new AccountResponse();

        response.setId(account.getId());
        response.setName(account.getName());
        response.setEmail(account.getEmail());
        response.setRole(account.getRole());
        response.setActive(account.isActive());

        // fetch from User entity
        response.setPhone(account.getUser().getPhone());
        response.setAddress(account.getUser().getAddress());
        response.setGender(account.getUser().getGender());
        response.setDateOfBirth(account.getUser().getDateOfBirth());

        return response;
    }
}
