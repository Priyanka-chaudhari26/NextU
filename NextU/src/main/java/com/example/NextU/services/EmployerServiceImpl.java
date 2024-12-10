package com.example.NextU.services;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.NextU.models.Employer;
import com.example.NextU.repositories.EmployerRepository;

@Service
public class EmployerServiceImpl implements EmployerService{
 
	@Autowired
	private EmployerRepository EmployerRepository;
	
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	
	@Override
	public boolean saveEmployer(Employer employer) {
		
		try {
			employer.setPassword(passwordEncoder.encode(employer.getPassword()));
			EmployerRepository.save(employer);
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Employer loginEmployer(String email, String password) {
		
		Employer validemployer = EmployerRepository.findByEmail(email); 
		 if (validemployer == null) {
		        System.out.println("No employer found with email: " + email);
		        return null;
		    }
		if (passwordEncoder.matches(password, validemployer.getPassword())) {
			return validemployer;
		}
		return null;
	}

	@Override
	public Employer findById(Long id) {
        Optional<Employer> employer = EmployerRepository.findById(id);
        return employer.orElse(null);  
    }
	
	public boolean existsByEmailOrMobileNumber(String email, String mobileNumber) {
	    return EmployerRepository.existsByEmail(email) || EmployerRepository.existsByMobileNumber(mobileNumber);
	}

}
