package com.wipro.simplyfly.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.wipro.simplyfly.dto.UserDTO;
import com.wipro.simplyfly.service.IUserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IUserService userService;

    // =============================
    // PROTECTED APIs (USER Role Required)
    // =============================

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable Long id,
                              @RequestBody UserDTO userDTO) {
        return userService.updateUser(id, userDTO);
    }

    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "User deleted successfully";
    }
}