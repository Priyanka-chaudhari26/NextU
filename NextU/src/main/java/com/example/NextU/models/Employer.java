package com.example.NextU.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "employer")
public class Employer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employer_id", nullable = false)
    private Long employerId;
    
    @Column(name = "full_name", length = 100, nullable = false)
    private String fullName;
    
    @Column(name = "account_type", length = 50, nullable = false)
    private String accountType;
    
    @Column(name = "mobile_number", length = 10, nullable = false)
    private String mobileNumber;
    
    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;
    
    @Column(name = "password", length = 255, nullable = false)
    private String password;
    
    
    //private String confirmPassword;
    
//    @Column(name = "otp", length = 6)
//    private String otp;
    @Column(name = "employer_company_name", length = 255, nullable = false)
    private String EmployercompanyName;

    @Column(name = "employer_company_address", length = 255, nullable = true)
    private String EmployercompanyAddress;

    @Column(name = "e_company_contact_number", length = 15, nullable = true)
    private String EmployercompanyContactNumber;
    
    @Column(name = "is_otp_verified", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean isOtpVerified = false;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    public Long getEmployerId() {
        return employerId;
    }

    public void setEmployerId(Long employerId) {
        this.employerId = employerId;
    }
    
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
    
    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
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

    
    public boolean isOtpVerified() {
        return isOtpVerified;
    }

    public void setOtpVerified(boolean otpVerified) {
        this.isOtpVerified = otpVerified;
    }
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    public String getEmployercompanyName() {
        return EmployercompanyName;
    }

    public void setEmployercompanyName(String EmployercompanyName) {
        this.EmployercompanyName = EmployercompanyName;
    }

    public String getEmployercompanyAddress() {
        return EmployercompanyAddress;
    }

    public void setEmployercompanyAddress(String EmployercompanyAddress) {
        this.EmployercompanyAddress = EmployercompanyAddress;
    }

    public String getEmployercompanyContactNumber() {
        return EmployercompanyContactNumber;
    }

    public void setEmployercompanyContactNumber(String companyContactNumber) {
        this.EmployercompanyContactNumber = EmployercompanyContactNumber;
    }


    
    
 }
