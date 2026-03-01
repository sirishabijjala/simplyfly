package com.wipro.simplyfly.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.wipro.simplyfly.entity.Account;
import com.wipro.simplyfly.repository.AccountRepository;

public class AccountInfoUserDetailsService implements UserDetailsService{
	
	@Autowired
    private AccountRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        Optional<Account> account = repository.findByEmail(email);

        return account
                .map(AccountInfoUserDetails::new)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));
    }

}
