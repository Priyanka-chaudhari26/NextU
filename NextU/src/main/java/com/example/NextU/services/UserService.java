package com.example.NextU.services;

import com.example.NextU.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.List;
import java.util.Optional;

public interface UserService {
	public boolean registerUser(User user);
	public Optional<User> authenticateUser(String email, String password);
	public Optional<User> findById(Long id);
	public User loginUser(String email, String password);
	public UserDetails loadUserByUsername(String email);
	public void updateUser(User user);
	public List<String> getRegisteredSkills();
}