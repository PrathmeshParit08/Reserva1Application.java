package com.reservaaa.reserva1.dto;

import com.reservaaa.reserva1.enums.UserRole;
import lombok.Data;

@Data
public class UserDTO {
    private long id;

    private String email;
    private String password;
    private String name;
    private String lastname;
    private String phone;
    private UserRole role;

}
