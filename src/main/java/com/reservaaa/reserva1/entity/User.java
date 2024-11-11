package com.reservaaa.reserva1.entity;

import com.reservaaa.reserva1.dto.UserDTO;
import com.reservaaa.reserva1.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String email;
    private String password;
    private String name;
    private String lastname;
    private String phone;
    private UserRole role;

    public UserDTO getDTO(){
        UserDTO user=new UserDTO();
        user.setId(id);
        user.setEmail(email);
        user.setName(name);
        user.setLastname(lastname);
        user.setPassword(password);
        user.setPhone(phone);
        user.setRole(role);
        return user;
    }


}
