package com.rooptech.bankingapp.auth.service;

import com.rooptech.bankingapp.auth.dto.LoginRequestDto;
import com.rooptech.bankingapp.auth.dto.LoginResponseDto;

public interface AuthService {
    LoginResponseDto customerLogin(LoginRequestDto loginRequestDto);
}
