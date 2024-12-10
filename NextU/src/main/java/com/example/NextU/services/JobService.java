package com.example.NextU.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;  // Import statement for List
import java.util.Optional;

import com.example.NextU.models.Application;
import com.example.NextU.models.Employer;
import com.example.NextU.models.Job;
import com.example.NextU.models.User;
import com.example.NextU.repositories.ApplicationRepository;
import com.example.NextU.repositories.JobRepository;

@Service
public interface JobService {

    public void saveJob(Job job);
    public List<Job> getAllJobs();
    public List<Job> getJobsByEmployer(Employer employer);
    public Job findByJobId(Long jobId);
    public Job getJobById(Long jobId);
    public void applyForJob(User user, Long jobId);
    public List<Application> getApplicationsByJob(Long jobId);
    public List<Application> getApplicationsByUser(User user);
    public void deleteJob(Long jobId);

}
