package com.example.NextU.services;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.NextU.models.Application;
import com.example.NextU.models.Job;
import com.example.NextU.models.User;
import com.example.NextU.repositories.ApplicationRepository;
import com.example.NextU.repositories.EmployerRepository;

@Service
public class ApplicationServiceImpl implements ApplicationService {
	
	@Autowired
	private ApplicationRepository applicationRepository;
	
	@Override
	public List<Application> getApplicationsByUser(User user) {
	    return applicationRepository.findByUser(user);
	}
	
	//-----------------------------------------------
	@Override
    public void saveApplication(Application application) {
        applicationRepository.save(application);
    }
    
	@Override
    public List<Application> getApplicationsByJob(Job job) {
        return applicationRepository.findByJob(job);
    }
    
	@Override
    public boolean isUserAppliedForJob(Long userId, Long jobId) {
        return applicationRepository.existsByUserUserIdAndJobJobId(userId, jobId);
    }
}
