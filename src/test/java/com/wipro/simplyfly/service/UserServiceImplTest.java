package com.wipro.simplyfly.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.wipro.simplyfly.dto.UserDTO;
import com.wipro.simplyfly.entity.User;
import com.wipro.simplyfly.exceptions.InvalidCredentialsException;
import com.wipro.simplyfly.exceptions.UserAlreadyExistsException;
import com.wipro.simplyfly.exceptions.UserNotFoundException;
import com.wipro.simplyfly.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {

        user = new User(
                "Test",
                "test@gmail.com",
                "encodedPass",
                "9999999999",
                "Hyderabad",
                "MALE",
                LocalDate.of(2000, 1, 1)
        );

        user.setId(1L);

        userDTO = new UserDTO();
        userDTO.setName("Test");
        userDTO.setEmail("test@gmail.com");
        userDTO.setPassword("pass123");
        userDTO.setPhone("9999999999");
        userDTO.setAddress("Hyderabad");
        userDTO.setGender("MALE");
        userDTO.setDateOfBirth(LocalDate.of(2000, 1, 1));
    }

    // ================= REGISTER =================
    @Test
    void testRegisterUser_Success() {

        when(userRepository.findByEmail(userDTO.getEmail()))
                .thenReturn(Optional.empty());

        when(passwordEncoder.encode(userDTO.getPassword()))
                .thenReturn("encodedPass");

        when(userRepository.save(any(User.class)))
                .thenReturn(user);

        UserDTO result = userService.registerUser(userDTO);

        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());
    }

    @Test
    void testRegisterUser_AlreadyExists() {

        when(userRepository.findByEmail(userDTO.getEmail()))
                .thenReturn(Optional.of(user));

        assertThrows(UserAlreadyExistsException.class, () -> {
            userService.registerUser(userDTO);
        });
    }

    // ================= GET BY ID =================
    @Test
    void testGetUserById_Found() {

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        UserDTO result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());
    }

    @Test
    void testGetUserById_NotFound() {

        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.getUserById(1L);
        });
    }

    // ================= GET ALL =================
    @Test
    void testGetAllUsers() {

        List<User> users = new ArrayList<>();
        users.add(user);

        when(userRepository.findAll()).thenReturn(users);

        List<UserDTO> result = userService.getAllUsers();

        assertEquals(1, result.size());
    }

    // ================= UPDATE =================
    @Test
    void testUpdateUser_Found() {

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(userRepository.save(any(User.class)))
                .thenReturn(user);

        UserDTO result = userService.updateUser(1L, userDTO);

        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());
    }

    @Test
    void testUpdateUser_NotFound() {

        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.updateUser(1L, userDTO);
        });
    }

    // ================= DELETE =================
    @Test
    void testDeleteUser() {

        when(userRepository.existsById(1L))
                .thenReturn(true);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    // ================= LOGIN =================
    @Test
    void testLoginUser_Success() {

        when(userRepository.findByEmail(user.getEmail()))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("pass123", user.getPassword()))
                .thenReturn(true);

        UserDTO result = userService.loginUser(user.getEmail(), "pass123");

        assertNotNull(result);
    }

    @Test
    void testLoginUser_Fail() {

        when(userRepository.findByEmail(user.getEmail()))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("wrong", user.getPassword()))
                .thenReturn(false);

        assertThrows(InvalidCredentialsException.class, () -> {
            userService.loginUser(user.getEmail(), "wrong");
        });
    }
}