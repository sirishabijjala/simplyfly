package com.wipro.simplyfly.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.simplyfly.dto.AuthResponse;
import com.wipro.simplyfly.dto.LoginRequest;
import com.wipro.simplyfly.dto.RegisterRequest;
import com.wipro.simplyfly.service.AccountService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
    private AccountService accountService;

    //  Register API
    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        return accountService.register(request);
    }

    //  Login API
    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return accountService.login(request);
    }

}
