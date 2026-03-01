package com.wipro.simplyfly.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public class UserDTO {

    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @Size(min = 6, max = 10,  message = "Password must be minimum 6 characters")
    @NotBlank(message = "Password cannot be null or empty")
    private String password;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String phone;

    @NotBlank(message = "Role is required")
    private String role;

    private boolean enabled;
    private LocalDateTime createdDate;

    public UserDTO() {
    }

    public UserDTO(Long id, String name, String email, String password,
                   String phone, String role, boolean enabled,
                   LocalDateTime createdDate) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.role = role;
        this.enabled = enabled;
        this.createdDate = createdDate;
    }

    // Getters and Setters

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getRole() { return role; }

    public void setRole(String role) { this.role = role; }

    public boolean isEnabled() { return enabled; }

    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public LocalDateTime getCreatedDate() { return createdDate; }

    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
}