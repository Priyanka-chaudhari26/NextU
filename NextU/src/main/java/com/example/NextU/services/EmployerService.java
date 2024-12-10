package com.example.NextU.services;
import com.example.NextU.models.Employer;

public interface EmployerService {
	public boolean saveEmployer(Employer employer);
	public Employer loginEmployer(String email, String password); // It will return employer after successful login.
	public Employer findById(Long id);
	public boolean existsByEmailOrMobileNumber(String email, String mobileNumber);
}
