package com.example.NextU.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.NextU.models.Employer;

public interface EmployerRepository extends JpaRepository<Employer, Long>{
	
	Employer findByEmail(String email);
	boolean existsByEmail(String email);
    boolean existsByMobileNumber(String mobileNumber);
}
