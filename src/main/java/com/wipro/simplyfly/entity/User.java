package com.wipro.simplyfly.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String role;   // USER / OWNER / ADMIN

    private boolean enabled = true;

    private LocalDateTime createdDate;

    // 🔥 OneToOne Relationship with Account
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", nullable = false, unique = true)
    private Account account;

    // OneToMany Relationship with Booking
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Booking> bookings;

    // ================= CONSTRUCTORS =================

    public User() {
        this.createdDate = LocalDateTime.now();
    }

    public User(String name, String email, String password,
                String phone, String role, Account account) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.role = role;
        this.enabled = true;
        this.createdDate = LocalDateTime.now();
        this.account = account;
    }

    // ================= GETTERS & SETTERS =================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}