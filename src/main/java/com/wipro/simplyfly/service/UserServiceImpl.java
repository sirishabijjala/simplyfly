package com.wipro.simplyfly.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wipro.simplyfly.dto.UserDTO;
import com.wipro.simplyfly.entity.Account;
import com.wipro.simplyfly.entity.User;
import com.wipro.simplyfly.exceptions.InvalidCredentialsException;
import com.wipro.simplyfly.exceptions.UserAlreadyExistsException;
import com.wipro.simplyfly.exceptions.UserNotFoundException;
import com.wipro.simplyfly.repository.UserRepository;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO registerUser(UserDTO userDTO) {

        // Check if user already exists
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException(userDTO.getEmail());
        }

        Account account = new Account();
        account.setName(userDTO.getName());
        account.setEmail(userDTO.getEmail());
        account.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        account.setRole(userDTO.getRole());
        account.setActive(true);

        User user = new User(
                userDTO.getName(),
                userDTO.getEmail(),
                passwordEncoder.encode(userDTO.getPassword()),
                userDTO.getPhone(),
                userDTO.getRole(),
                account
        );

        User savedUser = userRepository.save(user);

        return convertToDTO(savedUser);
    }

    @Override
    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        return convertToDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        user.setName(userDTO.getName());
        user.setPhone(userDTO.getPhone());
        user.setRole(userDTO.getRole());

        User updatedUser = userRepository.save(user);
        return convertToDTO(updatedUser);
    }

    @Override
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }
        userRepository.deleteById(userId);
    }

    @Override
    public UserDTO loginUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        return convertToDTO(user);
    }

    // ===== Mapping Method =====
    private UserDTO convertToDTO(User user) {
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