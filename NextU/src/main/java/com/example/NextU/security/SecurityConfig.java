package com.example.NextU.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.example.NextU.services.UserService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class SecurityConfig {

	private final UserDetailsService userService;

	
	public SecurityConfig(UserDetailsService userService) {
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(
                    "/", 
                    "/jobseeker-register", 
                    "/jobseeker-login", 
                    "/employer-register",
                    "/EmployerDashboard",
                    "/employer-login", 
                    "/otp-verification", 
                    "/job-description",
                    "/employer-dashboard",
                    "/employerlogout",
                    "/employer-profile",
                    "/registerEmployer",
                    "/verifyOtp",
                    "/createJob",
                    "/job-description/{jobId}",
                    "/apply/{jobId}",
                    "/view-candidates/{jobId}",
                    "/userverifyOtp",
                    "/login",
                    "/user-dashboard",
                    "/user-profile",
                    "/update-employer-profile",
                    "/update-user-profile",
                    "/job/{jobId}",
                    "/download-resume/{userId}",
                    "/user/profile/{id}",
                    "/application/status/{applicationId}",
                    "/delete-job/{jobId}",
                    "/userlogout"
                ).permitAll() 
                .anyRequest().authenticated() 
            )
     
        	.sessionManagement(sessionManagement -> sessionManagement
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS) 
            )
            .logout(logout -> logout
                .permitAll()
            );

        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }
}
//------------------------------------------------
//@Configuration
//public class SecurityConfig {
//
//    private final UserDetailsService userService;
//
//    public SecurityConfig(UserDetailsService userService) {
//        this.userService = userService;
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        String[] permittedUrls = {
//            "/", 
//            "/jobseeker-register", 
//            "/jobseeker-login", 
//            "/employer-register",
//            "/EmployerDashboard",
//            "/employer-login", 
//            "/otp-verification", 
//            "/job-description",
//            "/employer-dashboard",
//            "/registerEmployer",
//            "/verifyOtp",
//            "/createJob",
//            "/job-description/{jobId}",
//            "/view-candidates/{jobId}",
//            "/userverifyOtp",
//            "/login",
//            "/user-dashboard",
//            "/job/{jobId}",
//            "/apply/{jobId}"
//        };
//
//        http
//            .csrf().disable()
//            .authorizeHttpRequests(authorize -> {
//                for (String url : permittedUrls) {
//                    authorize.antMatcher(url).permitAll();
//                }
//                authorize.anyRequest().authenticated();
//            })
//            .sessionManagement(sessionManagement -> sessionManagement
//                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
//            )
//            .logout(logout -> logout
//                .permitAll()
//            );
//
//        return http.build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
