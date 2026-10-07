package com.rooptech.bankingapp.auth.service.impl;

import com.rooptech.bankingapp.account.entity.Customer;
import com.rooptech.bankingapp.account.repository.CustomerRepository;
import com.rooptech.bankingapp.auth.dto.LoginRequestDto;
import com.rooptech.bankingapp.auth.dto.LoginResponseDto;
import com.rooptech.bankingapp.auth.repository.RoleRepository;
import com.rooptech.bankingapp.auth.security.JwtUtil;
import com.rooptech.bankingapp.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final CompromisedPasswordChecker compromisedPasswordChecker;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public LoginResponseDto customerLogin(LoginRequestDto loginRequestDto) {

        Customer customer= customerRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(
                        ()-> new RuntimeException("Customer not found with given email")

                );

        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getEmail(),
                        loginRequestDto.getPassword()
                )
        );
        String token = jwtUtil.generateToken(authentication);
        return new LoginResponseDto("logged in successfully ", token);
    }
}