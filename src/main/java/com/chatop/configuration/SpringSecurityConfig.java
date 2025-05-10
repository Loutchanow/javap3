package com.chatop.configuration;


import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.source.ImmutableSecret;

@Configuration
public class SpringSecurityConfig {

	
    private static final String[] PUBLIC = {
          "/api/auth/login",
          "/api/auth/register",
  };

    @Value("${jwt.secret}")
    private String jwtKey;
    
	
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
	public UserDetailsService users() {
		UserDetails user = User.builder().username("user").password(passwordEncoder().encode("password")).roles("USER")
				.build();
		return new InMemoryUserDetailsManager(user);
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