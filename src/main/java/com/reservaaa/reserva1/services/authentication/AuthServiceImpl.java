package com.reservaaa.reserva1.services.authentication;

import com.reservaaa.reserva1.dto.SignUpRequestDTO;
import com.reservaaa.reserva1.dto.UserDTO;
import com.reservaaa.reserva1.entity.User;
import com.reservaaa.reserva1.enums.UserRole;
import com.reservaaa.reserva1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl  implements AuthService{
    @Autowired
    private UserRepository userRepository;


            //signup as a client authentication
    public UserDTO signUpClient(SignUpRequestDTO signUpRequestDTO){
            User user=new User();
            user.setName(signUpRequestDTO.getName());
            user.setLastname(signUpRequestDTO.getLastname());
            user.setEmail(signUpRequestDTO.getEmail());
            user.setPhone(signUpRequestDTO.getPhone());
            user.setPassword(new BCryptPasswordEncoder().encode(signUpRequestDTO.getPassword()));
            user.setRole(UserRole.CLIENT);

            return userRepository.save(user).getDTO();
    }

    //finding presence of client by their email

    public  Boolean findByEmail(String email){
        return userRepository.findFirstByEmail(email)!=null;
    }

    public UserDTO signUpServiceCompany(SignUpRequestDTO signUpRequestDTO){
        User user=new User();
        user.setName(signUpRequestDTO.getName());
//        user.setLastname(signUpRequestDTO.getLastname());
        user.setEmail(signUpRequestDTO.getEmail());
        user.setPhone(signUpRequestDTO.getPhone());
        user.setPassword(new BCryptPasswordEncoder().encode(signUpRequestDTO.getPassword()));
        user.setRole(UserRole.SERVICE_COMPANY);

        return userRepository.save(user).getDTO();
    }
}
