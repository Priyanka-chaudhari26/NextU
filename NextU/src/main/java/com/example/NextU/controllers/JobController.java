package com.example.NextU.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.NextU.models.Application;
import com.example.NextU.models.Employer;
import com.example.NextU.models.Job;
import com.example.NextU.models.User;
import com.example.NextU.repositories.ApplicationRepository;
import com.example.NextU.repositories.JobRepository;
import com.example.NextU.repositories.UserRepository;
import com.example.NextU.services.ApplicationService;
import com.example.NextU.services.EmployerService;
import com.example.NextU.services.JobService;
import com.example.NextU.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Controller
public class JobController {
	
    @Autowired
    private JobService jobService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplicationRepository applicationRepository;
    
    @Autowired
    private EmployerService employerservice;
    
    @Autowired
    private ApplicationService applicationService;

    @GetMapping("/employer-dashboard")
    public String showEmployerDashboard(Model model, @RequestParam("employerId") Long employerId, HttpSession session) {
    	Employer employer = (Employer) session.getAttribute("employer");
    	if(employer != null) {
	    	Employer employ = employerservice.findById(employerId);
	    	List<Job> jobs = jobService.getJobsByEmployer(employ);
	    	model.addAttribute("jobs", jobs);
	        model.addAttribute("job", new Job());
	        model.addAttribute("employerId", employerId);
	        model.addAttribute("employer", employer);
	       
	        return "employer-dashboard"; 
        
    	}
    	else {
    		return "redirect:/employer-login";
    	}
    }

    @PostMapping("/createJob")
    public String addJob(@ModelAttribute("job") Job job, Model model, @RequestParam("employerId") Long employerId) {
    	Employer employer = employerservice.findById(employerId);
        job.setEmployer(employer);
        jobService.saveJob(job);  
        model.addAttribute("successMsg", "Job posted successfully!");
        return "redirect:/employer-dashboard?employerId=" + employerId;
    }
    
    @GetMapping("/job-description/{jobId}")
    public String showJobDescription(@PathVariable("jobId") Long jobId, Model model) {
        Job job = jobService.findByJobId(jobId);
        model.addAttribute("job", job);
        return "job-description";
    }

	  @GetMapping("/job/{jobId}")
	  public String getJobDetails(@PathVariable Long jobId, Model model, HttpSession session) {
	      Job job = jobService.findByJobId(jobId);
	      User user = (User) session.getAttribute("user");
	      Application application = applicationRepository.findByUserAndJob(user, job).orElse(null);

	      if (application != null) {
	          System.out.println("Application Status: " + application.getStatus());
	      } else {
	          System.out.println("Application not found");
	      }
	      boolean isApplied = applicationService.isUserAppliedForJob(user.getUserId(), jobId);
	      model.addAttribute("job", job);
	      model.addAttribute("isApplied", isApplied);
//	      model.addAttribute("application", application);
	      model.addAttribute("apps", application);

	      System.out.println("Application: " + application);
	      return "jobDetails";
	  }
	    

    //--------------
    @PostMapping("/apply/{jobId}")
    public String applyForJob(@PathVariable Long jobId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            Job job = jobService.getJobById(jobId);
        	//Job job = jobRepository.findById(jobId)
            if (job != null) {
                Application application = new Application(user, job);
                applicationService.saveApplication(application);
                return "redirect:/user-dashboard?success";
            } else {
                
                return "redirect:/user-dashboard?error";
            }
        } else {
            
            return "redirect:/login";
        }
    }
    
    @Transactional
    @PostMapping("/delete-job/{jobId}")
    public String deleteJob(@PathVariable Long jobId, HttpSession session) {
        Employer employer = (Employer) session.getAttribute("employer");

        if (employer != null) {
            
            Job job = jobService.getJobById(jobId);
            if (job != null && job.getEmployer().getEmployerId().equals(employer.getEmployerId())) {
               
            	applicationRepository.deleteByJob_JobId(jobId);
                jobService.deleteJob(jobId);
                return "redirect:/employer-dashboard?employerId=" + employer.getEmployerId(); 
            }
        }
        
        return "redirect:/employer-dashboard";
    }
}
