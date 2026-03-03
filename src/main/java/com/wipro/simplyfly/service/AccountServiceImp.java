package com.wipro.simplyfly.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wipro.simplyfly.dto.AuthResponse;
import com.wipro.simplyfly.dto.LoginRequest;
import com.wipro.simplyfly.dto.RegisterRequest;
import com.wipro.simplyfly.entity.Account;
import com.wipro.simplyfly.entity.FlightOwner;
import com.wipro.simplyfly.entity.User;
import com.wipro.simplyfly.exceptions.EmailAlreadyExistsException;
import com.wipro.simplyfly.repository.AccountRepository;
import com.wipro.simplyfly.repository.FlightOwnerRepository;
import com.wipro.simplyfly.repository.UserRepository;
@Service
public class AccountServiceImp implements AccountService{

	   @Autowired
	    private AccountRepository repository;

	    @Autowired
	    private PasswordEncoder passwordEncoder;
	    
	    @Autowired
	    private UserRepository userRepo;

	    @Autowired
	    private FlightOwnerRepository ownerRepo;
	    @Autowired
	    private JwtService jwtService;

	    //REGISTER METHOD
	    @Override
	    public String register(RegisterRequest request) {

	        // check if email already exists
	        if (repository.findByEmail(request.getEmail()).isPresent()) {
	        	throw new EmailAlreadyExistsException("Email already registered");
	        }

	        Account account = new Account();
	        account.setName(request.getName());
	        account.setEmail(request.getEmail());
	        account.setPassword(passwordEncoder.encode(request.getPassword()));
	        account.setRole(request.getRole());   // USER / ADMIN / OWNER
	        account.setActive(true);

	        repository.save(account);
	        
	     //  Decide which Profile to create
	        if (request.getRole().equalsIgnoreCase("USER")) {
	            User user = new User();
	            user.setName(request.getName());
	            user.setEmail(request.getEmail());
	            user.setPassword(request.getPassword());
	            user.setPhone(request.getPhone());
	            user.setEnabled(true);
	            user.setCreatedDate(LocalDateTime.now());
	            user.setRole(request.getRole());
	            user.setAccount(account); 
	            userRepo.save(user);
	        } 
	        else if (request.getRole().equalsIgnoreCase("OWNER")) {
	            FlightOwner owner = new FlightOwner();
	            owner.setName(request.getName());
	            owner.setEmail(request.getEmail());
	            owner.setAccount(account); 
	            ownerRepo.save(owner);
	        }
	        
	        return "Registration Successful for " + request.getRole();

	      
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
	        String token = jwtService.generateToken(
	                account.getEmail(),   
	                account.getRole()     
	        );

	        return new AuthResponse(
	                token,
	                account.getRole(),
	                "Login Successful"
	        );
	    }

}
