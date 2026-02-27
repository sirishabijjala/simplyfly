package com.wipro.simplyfly.service;

import java.util.List;

import com.wipro.simplyfly.dto.UserDTO;

public interface IUserService {

  
    UserDTO registerUser(UserDTO userDTO);

   
    UserDTO getUserById(Long userId);

    List<UserDTO> getAllUsers();

    UserDTO updateUser(Long userId, UserDTO userDTO);

  
    void deleteUser(Long userId);

    UserDTO loginUser(String email, String password);
}