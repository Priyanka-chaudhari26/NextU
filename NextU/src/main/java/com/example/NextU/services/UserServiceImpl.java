package com.example.NextU.services;

import com.example.NextU.models.User;
import com.example.NextU.repositories.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

   
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public boolean registerUser(User user) {
        
    	try {
    		user.setPassword(passwordEncoder.encode(user.getPassword()));

    		if (user.getPreviousCompany() != null) {
    		    System.out.println("Previous Company: " + user.getPreviousCompany());
    		} else {
    		    System.out.println("Previous Company is null");
    		}

    		userRepository.save(user);
            return true;
    	}
    	catch(Exception e){
			e.printStackTrace();
			return false;
		}
        
    }

    @Override
    public Optional<User> authenticateUser(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            
            if (passwordEncoder.matches(password, user.getPassword())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
    
    @Override
    public User loginUser(String email, String password) {
        
        Optional<User> optionalUser = userRepository.findByEmail(email);

       
        if (optionalUser.isPresent()) {
            User validUser = optionalUser.get();  
            if (passwordEncoder.matches(password, validUser.getPassword())) {
                return validUser;
            }
        }
        return null; 
    }
    
 
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles("ROLE_JOBSEEKER")  
                .build();
    }
    
    @Transactional
    @Override
    public void updateUser(User user) {
        User existingUser = userRepository.findById(user.getUserId()).orElseThrow(
            () -> new RuntimeException("User not found with id: " + user.getUserId())
        );

        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhone(user.getPhone());
        existingUser.setTenthScore(user.getTenthScore());
        existingUser.setTwelfthScore(user.getTwelfthScore());
        existingUser.setGraduationScore(user.getGraduationScore());
        existingUser.setPostgraduationScore(user.getPostgraduationScore());
        existingUser.setLocation(user.getLocation());
        existingUser.setSkills(user.getSkills());
        existingUser.setPreviousCompany(user.getPreviousCompany());
        existingUser.setExperienceYears(user.getExperienceYears());
        existingUser.setExperienceMonths(user.getExperienceMonths());
        existingUser.setResume(user.getResume());

        userRepository.save(existingUser);
    }
    

    @Override
    public List<String> getRegisteredSkills() {
        return userRepository.findAll().stream()
                .flatMap(user -> user.getSkills().stream())
                .distinct()
                .collect(Collectors.toList());
    }
}