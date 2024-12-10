package com.example.NextU.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;  
import java.util.Optional;
import com.example.NextU.models.Application;
import com.example.NextU.models.Employer;
import com.example.NextU.models.Job;
import com.example.NextU.models.User;
import com.example.NextU.repositories.ApplicationRepository;
import com.example.NextU.repositories.JobRepository;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;
    
    @Autowired
    private ApplicationRepository applicationRepository;

    @Override
    public void saveJob(Job job) {
        jobRepository.save(job);  
    }

    @Override
    public List<Job> getAllJobs() {
        return jobRepository.findAll();  
    }
    
    @Override
    public List<Job> getJobsByEmployer(Employer employer) {
        return jobRepository.findByEmployer(employer);
    }
    
    @Override
    public Job findByJobId(Long jobId) {
        return jobRepository.findByJobId(jobId);
    }
    
    @Override
    public Job getJobById(Long jobId) {
        return jobRepository.findById(jobId).orElse(null);
    }
    
    @Override
    public void applyForJob(User user, Long jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        Application application = new Application();
        application.setUser(user);
        application.setJob(job);
        application.setName(user.getName());
        application.setTitle(job.getTitle());
        application.setApplicationDate(new Timestamp(System.currentTimeMillis()));

        applicationRepository.save(application);
    }
    
    @Override
    public List<Application> getApplicationsByJob(Long jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        return applicationRepository.findByJob(job);
    }

    @Override
    public List<Application> getApplicationsByUser(User user) {
        return applicationRepository.findByUser(user);
    }
    
    @Override
    public void deleteJob(Long jobId) {
        jobRepository.deleteById(jobId); 
    }

}
