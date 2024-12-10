package com.example.NextU.controllers;
import org.springframework.web.bind.annotation.*;

import com.example.NextU.models.Application;
import com.example.NextU.repositories.ApplicationRepository;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/application")
public class ApplicationController {

	@Autowired
    private ApplicationRepository applicationRepository;

    @PostMapping("/status/{applicationId}")
    public String updateApplicationStatus(@PathVariable Long applicationId, @RequestParam String status) {
        Application application = applicationRepository.findById(applicationId).orElseThrow(() -> new RuntimeException("Application not found"));
        application.setStatus(status);
        applicationRepository.save(application);
        return "redirect:/employerApplications"; 
    }
}
