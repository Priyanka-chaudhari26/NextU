package com.example.NextU.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.NextU.models.Application;
import com.example.NextU.models.Job;
import com.example.NextU.models.User;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    
	List<Application> findByJob(Job job);
	List<Application> findByUser(User user);
	List<Application> findByJob_JobId(Long jobId); 
	boolean existsByUserUserIdAndJobJobId(Long userId, Long jobId);
	Optional<Application> findByUserAndJob(User user, Job job);
	void deleteByJob_JobId(Long jobId);
}
