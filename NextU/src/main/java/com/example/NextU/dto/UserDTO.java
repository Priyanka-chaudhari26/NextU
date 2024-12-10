package com.example.NextU.dto;

import java.sql.Timestamp;
import java.util.List;

public class UserDTO {

    private Long userId;
    private String name;
    private String email;
    private String phone;
    private Double tenthScore;
    private Double twelfthScore;
    private Double graduationScore;
    private Double postgraduationScore;
    private String location;
    private List<String> skills;
    private String previousCompany;
    private Integer experienceYears;
    private Integer experienceMonths;
    private byte[] resume;
    private String resumeFileName;
    private boolean isOtpVerified;
    //private Timestamp createdAt;

    

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

	public byte[] getResume() {
		return resume;
	}

	public void setResume(byte[] resume) {
		this.resume = resume;
	}

//    public Timestamp getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(Timestamp createdAt) {
//        this.createdAt = createdAt;
//    }
}
