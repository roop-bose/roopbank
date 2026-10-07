package com.rooptech.bankingapp.auth.security;

import com.rooptech.bankingapp.account.entity.Customer;
import com.rooptech.bankingapp.account.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final CustomerRepository customerRepository;
    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        Customer customer = customerRepository.findByEmailWithRoles(email)
                .orElseThrow(
                        ()-> new RuntimeException(
                                "Customer not found with given email"
                        )
                );

        return new User(
                customer.getEmail(),
                customer.getHashedPassword(),
                customer.getRoles().stream()
                        .map(role ->
                                new SimpleGrantedAuthority(role.getRoleName()))
                        .toList()
        );
    }
}