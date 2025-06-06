package com.chatop.configuration;


import java.util.ArrayList;
import java.util.List;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.chatop.model.Users;
import com.chatop.repository.UsersRepository;
import com.nimbusds.jose.jwk.source.ImmutableSecret;

@Configuration
public class SpringSecurityConfig {

	
    private static final String[] PUBLIC = {
          "/api/auth/login",
          "/api/auth/register",
          "/v3/api-docs/**",
          "/swagger-ui.html",
          "/swagger-ui/**" ,
          "/api/images/**"
          
  };

    @Value("${jwt.secret}")
    private String jwtKey;
    
    @Autowired
    private UsersRepository userRepository;
	
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       return http
          .csrf(csrf -> csrf.disable())
          .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
          .authorizeHttpRequests(auth -> auth
          .requestMatchers(PUBLIC).permitAll()
          .anyRequest().authenticated()
          )
          .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()))
          .httpBasic(Customizer.withDefaults()).build();
    }

	@Bean
	public JwtEncoder jwtEncoder() {
		return new NimbusJwtEncoder(new ImmutableSecret<>(this.jwtKey.getBytes()));
	}

	@Bean
	public JwtDecoder jwtDecoder() {
		SecretKeySpec secretKey = new SecretKeySpec(this.jwtKey.getBytes(), 0, this.jwtKey.getBytes().length, "RSA");
		return NimbusJwtDecoder.withSecretKey(secretKey).macAlgorithm(MacAlgorithm.HS256).build();
	}

	@Bean
	public UserDetailsService userDetailsService() {
	    return username -> {
	        Users user = userRepository.findByEmail(username)
	            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
	        return User.withUsername(user.getEmail())
	                .password(user.getPassword())
	                .roles("USER")
	                .build();
	    };
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
	    return config.getAuthenticationManager();
	}
}
