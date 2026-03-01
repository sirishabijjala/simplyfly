package com.wipro.simplyfly.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wipro.simplyfly.dto.AuthResponse;
import com.wipro.simplyfly.dto.LoginRequest;
import com.wipro.simplyfly.dto.RegisterRequest;
import com.wipro.simplyfly.entity.Account;
import com.wipro.simplyfly.repository.AccountRepository;
@Service
public class AccountServiceImp implements AccountService{
	
	   @Autowired
	    private AccountRepository repository;

	    @Autowired
	    private PasswordEncoder passwordEncoder;

	    @Autowired
	    private JwtService jwtService;

	    //REGISTER METHOD
	    @Override
	    public String register(RegisterRequest request) {

	        // check if email already exists
	        if (repository.findByEmail(request.getEmail()).isPresent()) {
	            return "Email already exists";
	        }

	        Account account = new Account();
	        account.setName(request.getName());
	        account.setEmail(request.getEmail());
	        account.setPassword(passwordEncoder.encode(request.getPassword()));
	        account.setRole(request.getRole());   // USER / ADMIN / OWNER
	        account.setActive(true);

	        repository.save(account);

	        return "Account Registered Successfully";
	    }

	    //LOGIN METHOD
	    @Override
	    public AuthResponse login(LoginRequest request) {

	        Account account = repository.findByEmail(request.getEmail())
	                .orElseThrow(() -> new RuntimeException("User not found"));

	        // check password
	        if (!passwordEncoder.matches(request.getPassword(), account.getPassword())) {
	            throw new RuntimeException("Invalid Password");
	        }

	        // generate token
	        String token = jwtService.generateToken(account.getEmail());

	        return new AuthResponse(
	                token,
	                account.getRole(),
	                "Login Successful"
	        );
	    }

}
