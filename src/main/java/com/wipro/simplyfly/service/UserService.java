package com.wipro.simplyfly.service;

import com.wipro.simplyfly.dto.UserDTO;

public interface UserService {

    UserDTO registerUser(UserDTO userDTO);

    UserDTO getUserById(Long userId);
}