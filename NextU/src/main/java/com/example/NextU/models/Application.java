package com.example.NextU.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Application {
	
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @CreationTimestamp
    @Column(name = "applicationDate", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp applicationDate;
    
    private String name; //username
    private String title; //jobtitle
    
    @Column(nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'pending'")
    private String status = "pending" ;
    
    @Override
    public String toString() {
        return "Application{" +
                "applicationId=" + applicationId +
                ", status='" + status + '\'' +
                ", user=" + (user != null ? user.getName() : "null") +
                ", job=" + (job != null ? job.getTitle() : "null") +
                ", applicationDate=" + applicationDate +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    
    public Application() {
    	this.status = "pending";
    } 
    
    public Application(User user, Job job) {
        this.user = user;
        this.job = job;
    }
    
    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Timestamp getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Timestamp applicationDate) {
        this.applicationDate = applicationDate;
    }

	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
	    return status;
	}

	public void setStatus(String status) {
	    this.status = status;
	}

}

