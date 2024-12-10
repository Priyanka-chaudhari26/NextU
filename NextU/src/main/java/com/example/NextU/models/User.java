package com.example.NextU.models;

import java.sql.Timestamp;
import java.util.List;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.JoinColumn;


@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId", nullable = false)
    private Long userId;
    
    @Column(name = "name", length = 100, nullable = false)
    private String name;
    
    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;
    
    @Column(name = "password", length = 255, nullable = false, unique = true)
    private String password;

    
    @Column(name = "phone", length = 10, nullable = false)
    private String phone;
       
    @Column(name = "tenthScore", nullable = false)
    private Double tenthScore;
    
    @Column(name = "twelfthScore", nullable = false)
    private Double twelfthScore;
    
    @Column(name = "graduationScore", nullable = true)
    private Double graduationScore;
    
    @Column(name = "postgraduationScore", nullable = true)
    private Double postgraduationScore; 
    
    @Column(name = "location", nullable = false)
    private String location;
    
   
    
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_skills", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "skills", nullable = true)
    private List<String> skills; 
       
    @Column(name = "previousCompany",  nullable = true)
    private String previousCompany;
    
    @Column(name = "experienceYears", nullable = true)
    private Integer experienceYears;

    @Column(name = "experienceMonths", nullable = true)
    private Integer experienceMonths;

    @Lob
    @Column(name = "resume", nullable = true, columnDefinition = "MEDIUMBLOB")
    private byte[] resume;  
    
    @Column(name = "resumeFileName", nullable = true)
    private String resumeFileName;

    @Column(name = "is_otp_verified", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isOtpVerified = false;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;
    
    @OneToMany(mappedBy = "user")
    private List<Application> applications;
    
    
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getTenthScore() {
        return tenthScore;
    }

    public void setTenthScore(Double tenthScore) {
        this.tenthScore = tenthScore;
    }

    public Double getTwelfthScore() {
        return twelfthScore;
    }

    public void setTwelfthScore(Double twelfthScore) {
        this.twelfthScore = twelfthScore;
    }

    public Double getGraduationScore() {
        return graduationScore;
    }

    public void setGraduationScore(Double graduationScore) {
        this.graduationScore = graduationScore;
    }
    
    public Double getPostgraduationScore() {
		return postgraduationScore;
	}

	public void setPostgraduationScore(Double postgraduationScore) {
		this.postgraduationScore = postgraduationScore;
	}
	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public String getPreviousCompany() {
        return previousCompany;
    }

    public void setPreviousCompany(String previousCompany) {
        this.previousCompany = previousCompany;
    }

    public Integer getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(Integer experienceYears) {
        this.experienceYears = experienceYears;
    }

    public Integer getExperienceMonths() {
        return experienceMonths;
    }

    public void setExperienceMonths(Integer experienceMonths) {
        this.experienceMonths = experienceMonths;
    }
    
    public byte[] getResume() {
        return resume;
    }

    public void setResume(byte[] resume) {
        this.resume = resume;
    }

    public String getResumeFileName() {
        return resumeFileName;
    }

    public void setResumeFileName(String resumeFileName) {
        this.resumeFileName = resumeFileName;
    }
    
	public boolean isOtpVerified() {
        return isOtpVerified;
    }

    public void setOtpVerified(boolean otpVerified) {
        this.isOtpVerified = otpVerified;
    }
    
    

    
}