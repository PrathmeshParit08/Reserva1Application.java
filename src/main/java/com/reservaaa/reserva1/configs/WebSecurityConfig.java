package com.reservaaa.reserva1.configs;

import com.reservaaa.reserva1.services.jwt.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity

public class WebSecurityConfig {

    @Autowired
    private JwtRequestFilter requestFilter;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

            return http.csrf().disable()
                    .authorizeHttpRequests()
                    .requestMatchers("/authenticate","/company/signup","/client/signup","/ads","/search/{service}")
                    .permitAll().and()
                    .authorizeHttpRequests()
                    .requestMatchers("/api/**").authenticated().and()
                    .sessionManagement().
                    sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().
                    addFilterBefore(requestFilter, UsernamePasswordAuthenticationFilter.class).build();
        }

        @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)throws Exception{
            return config.getAuthenticationManager();
        }

        @Bean
    public PasswordEncoder passwordEncoder(){
            return new BCryptPasswordEncoder();
        }
}

//package com.reservaaa.reserva1.configs;
//
//import com.reservaaa.reserva1.services.jwt.JwtRequestFilter;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.authentication.AuthenticationEntryPointFailureHandler;
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
//public class WebSecurityConfig {
//
//    @Autowired
//    private JwtRequestFilter requestFilter;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .cors().and() // Add this if your application requires CORS support
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/authenticate", "/company/signup", "/client/signup", "/ads", "/search/{service}")
//                        .permitAll()
//                        .requestMatchers("/api/**")
//                        .authenticated()
//                )
//                .exceptionHandling()
//                .authenticationEntryPoint((request, response, authException) -> {
//                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Authentication required");
//                }).and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .addFilterBefore(requestFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//        return config.getAuthenticationManager();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
