package com.wipro.simplyfly.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.simplyfly.dto.UserDTO;
import com.wipro.simplyfly.entity.User;
import com.wipro.simplyfly.repository.UserRepository;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO registerUser(UserDTO userDTO) {

        // Convert DTO → Entity
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setPhone(userDTO.getPhone());
        user.setRole(userDTO.getRole());
        user.setEnabled(true);

        // Save into DB
        User savedUser = userRepository.save(user);

        // Convert Entity → DTO
        UserDTO responseDTO = new UserDTO();
        responseDTO.setId(savedUser.getId());
        responseDTO.setName(savedUser.getName());
        responseDTO.setEmail(savedUser.getEmail());
        responseDTO.setPhone(savedUser.getPhone());
        responseDTO.setRole(savedUser.getRole());
        responseDTO.setEnabled(savedUser.isEnabled());
        responseDTO.setCreatedDate(savedUser.getCreatedDate());

        return responseDTO;
    }

    @Override
    public UserDTO getUserById(Long userId) {

        User user = userRepository.findById(userId)
                .orElse(null);

        if (user == null) {
            return null;
        }

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setRole(user.getRole());
        dto.setEnabled(user.isEnabled());
        dto.setCreatedDate(user.getCreatedDate());

        return dto;
    }
}