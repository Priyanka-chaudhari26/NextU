package com.example.NextU.services;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.NextU.models.Application;
import com.example.NextU.models.Job;
import com.example.NextU.models.User;
import com.example.NextU.repositories.ApplicationRepository;
import com.example.NextU.repositories.EmployerRepository;

public interface ApplicationService {
	
	public List<Application> getApplicationsByUser(User user);
	public void saveApplication(Application application);
    
    public List<Application> getApplicationsByJob(Job job) ;
    public boolean isUserAppliedForJob(Long userId, Long jobId);
}
