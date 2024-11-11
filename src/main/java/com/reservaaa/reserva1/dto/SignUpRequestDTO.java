package com.reservaaa.reserva1.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SignUpRequestDTO {

    private long id;

    private String email;
    private String password;
    private String name;
    private String lastname;
    private String phone;


    public String getEmail() {
        return email;
    }
}
