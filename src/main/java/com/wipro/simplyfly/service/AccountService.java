package com.wipro.simplyfly.service;

import com.wipro.simplyfly.dto.AuthResponse;
import com.wipro.simplyfly.dto.LoginRequest;
import com.wipro.simplyfly.dto.RegisterRequest;

public interface AccountService {
	
	public String register(RegisterRequest request);

    public AuthResponse login(LoginRequest request);

}
