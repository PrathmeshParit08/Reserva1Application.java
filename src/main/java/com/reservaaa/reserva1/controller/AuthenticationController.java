package com.reservaaa.reserva1.controller;

import com.reservaaa.reserva1.dto.AuthenticationRequest;
import com.reservaaa.reserva1.dto.SignUpRequestDTO;
import com.reservaaa.reserva1.dto.UserDTO;
import com.reservaaa.reserva1.entity.User;
import com.reservaaa.reserva1.repository.UserRepository;
import com.reservaaa.reserva1.services.authentication.AuthService;
import com.reservaaa.reserva1.services.jwt.UserDetailsServiceImpl;
import com.reservaaa.reserva1.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private UserRepository userRepository;

    public static final String TOKEN_PREFIX="Bearer ";
    public static final String HEADER_STRING="Authentication";


        @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/client/signup")
    public ResponseEntity<?> signupClient(@RequestBody SignUpRequestDTO signUpRequestDTO){

        if(authService.findByEmail(signUpRequestDTO.getEmail())){
            return new ResponseEntity<>("USer with this email already exists", HttpStatus.NOT_ACCEPTABLE);

        }
        UserDTO createdUser=authService.signUpClient(signUpRequestDTO);
        return new ResponseEntity<>(createdUser,HttpStatus.OK);
    }

    @PostMapping("/company/signup")
    public ResponseEntity<?> signupServiceCompany(@RequestBody SignUpRequestDTO signUpRequestDTO){

        if(authService.findByEmail(signUpRequestDTO.getEmail())){
            return new ResponseEntity<>("Dealer with this email already exists", HttpStatus.NOT_ACCEPTABLE);

        }
        UserDTO createdUser=authService.signUpServiceCompany(signUpRequestDTO);
        return new ResponseEntity<>(createdUser,HttpStatus.OK);
    }


//    @PostMapping("/authenticate")
//    public void createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,
//                                          @RequestBody HttpServletResponse response) throws IOException, JSONException {
//
//        try{
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                    authenticationRequest.getUsername(),
//                    authenticationRequest.getPassword()
//            ));
//        }catch(BadCredentialsException e){
//            throw new BadCredentialsException("Incorrect username or password ",e);
//        }
//
//        final UserDetails userDetails=userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
//        final  String jwt =jwtUtil.generateToken(userDetails.getUsername());
//        User user=userRepository.findFirstByEmail(authenticationRequest.getUsername());
//
//        response.getWriter().write(new JSONObject().
//        put("userId",user.getId()).
//        put("role",user.getRole()).toString()
//        );
//
//        response.addHeader("Access-Control-Expose-Headers","Authorization");
//        response.addHeader("Access-Control-Allow-Headers","Authorization"+
//                "X-PINGOTHER, Origin,X-Requested-With ,content-Type,Accept,X-Custom-header");
//        response.addHeader(HEADER_STRING,TOKEN_PREFIX+jwt
//        );
//    }
@PostMapping("/authenticate")
public void createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,
                                      HttpServletResponse response) throws IOException, JSONException {

    try {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(),
                authenticationRequest.getPassword()
        ));
    } catch (BadCredentialsException e) {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Incorrect username or password");
        return;
    }

    final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
    final String jwt = jwtUtil.generateToken(userDetails.getUsername());
    User user = userRepository.findFirstByEmail(authenticationRequest.getUsername());

    response.setContentType("application/json");
    response.getWriter().write(new JSONObject()
            .put("userId", user.getId())
            .put("role", user.getRole())
            .put("token", jwt)
            .toString()
    );

    response.addHeader("Access-Control-Expose-Headers", "Authorization");
    response.addHeader("Authorization", TOKEN_PREFIX + jwt);
}

}
