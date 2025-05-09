package com.chatop.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    private static final String[] PUBLIC_ROUTES = {
        "/api/auth/login",
        "/api/auth/register"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(PUBLIC_ROUTES).permitAll()
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults());  
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


//import com.openclassrooms.chatop.service.CustomUserDetailsService;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig  {
//
//    private final CustomUserDetailsService userDetailsService;
//    private final JwtAuthorizationFilter jwtAuthorizationFilter;
//
//    public SecurityConfig(CustomUserDetailsService customUserDetailsService, JwtAuthorizationFilter jwtAuthorizationFilter) {
//        this.userDetailsService = customUserDetailsService;
//        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder PasswordEncoder)
//            throws Exception {
//        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
//        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(PasswordEncoder);
//        return authenticationManagerBuilder.build();
//    }
//
//
//    private static final String[] WHITELIST = {
//            "/api/auth/login",
//            "/api/auth/register",
//            "/api/api-docs/**",
//            "/api/swagger-ui/**"
//    };
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        http.csrf().disable()
//                .authorizeRequests()
//                .requestMatchers(WHITELIST).permitAll()
//                .anyRequest().authenticated()
//                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and().addFilterBefore(jwtAuthorizationFilter,UsernamePasswordAuthenticationFilter.class);
//
//
//        return http.build();
//    }
//
//
//    @Bean
//    public static PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    }