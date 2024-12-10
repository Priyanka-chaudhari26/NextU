package com.example.NextU.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.NextU.models.Employer;
import com.example.NextU.models.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
	List<Job> findByEmployer(Employer employer);
	Job findByJobId(Long jobId);
}
