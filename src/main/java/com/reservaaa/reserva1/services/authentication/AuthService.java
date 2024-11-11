package com.reservaaa.reserva1.services.authentication;

import com.reservaaa.reserva1.dto.SignUpRequestDTO;
import com.reservaaa.reserva1.dto.UserDTO;

public interface AuthService {
    UserDTO signUpClient(SignUpRequestDTO signUpRequestDTO);
    Boolean findByEmail(String email);
    UserDTO signUpServiceCompany(SignUpRequestDTO signUpRequestDTO);
}
