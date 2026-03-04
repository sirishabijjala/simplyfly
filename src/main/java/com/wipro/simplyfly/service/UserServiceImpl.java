package com.wipro.simplyfly.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wipro.simplyfly.dto.UserDTO;
import com.wipro.simplyfly.entity.User;
import com.wipro.simplyfly.exceptions.InvalidCredentialsException;
import com.wipro.simplyfly.exceptions.UserAlreadyExistsException;
import com.wipro.simplyfly.exceptions.UserNotFoundException;
import com.wipro.simplyfly.repository.UserRepository;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ================= REGISTER =================
    @Override
    public UserDTO registerUser(UserDTO userDTO) {

        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException(userDTO.getEmail());
        }

        User user = new User(
                userDTO.getName(),
                userDTO.getEmail(),
                passwordEncoder.encode(userDTO.getPassword()),
                userDTO.getPhone(),
                userDTO.getAddress(),
                userDTO.getGender(),
                userDTO.getDateOfBirth()
        );

        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    // ================= GET BY ID =================
    @Override
    public UserDTO getUserById(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        return convertToDTO(user);
    }

    // ================= GET ALL =================
    @Override
    public List<UserDTO> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ================= UPDATE =================
    @Override
    public UserDTO updateUser(Long userId, UserDTO userDTO) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setAddress(userDTO.getAddress());
        user.setGender(userDTO.getGender());
        user.setDateOfBirth(userDTO.getDateOfBirth());

        User updatedUser = userRepository.save(user);
        return convertToDTO(updatedUser);
    }

    // ================= DELETE =================
    @Override
    public void deleteUser(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }

        userRepository.deleteById(userId);
    }

    // ================= LOGIN =================
    @Override
    public UserDTO loginUser(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        return convertToDTO(user);
    }

    // ================= DTO MAPPER =================
    private UserDTO convertToDTO(User user) {

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setAddress(user.getAddress());
        dto.setGender(user.getGender());
        dto.setDateOfBirth(user.getDateOfBirth());

        return dto;
    }
}