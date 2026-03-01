package com.wipro.simplyfly.config;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.wipro.simplyfly.entity.Account;

public class AccountInfoUserDetails implements UserDetails{
	
	private String email;
    private String password;
    private boolean active;
    private List<GrantedAuthority> authorities;

    public AccountInfoUserDetails(Account account) {
        this.email = account.getEmail();
        this.password = account.getPassword();
        this.active = account.isActive();
        this.authorities =
                List.of(new SimpleGrantedAuthority(account.getRole()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;  // email is username
    }

    @Override
    public boolean isAccountNonExpired() {
        return active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return active;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

}
