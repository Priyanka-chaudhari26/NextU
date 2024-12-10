package com.example.NextU.controllers;

import java.security.Principal;
import java.util.regex.*;
import java.util.List;

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
import com.example.NextU.repositories.ApplicationRepository;
import com.example.NextU.repositories.EmployerRepository;
import com.example.NextU.repositories.JobRepository;
import com.example.NextU.services.EmailService;
import com.example.NextU.services.EmployerService;

import jakarta.servlet.http.HttpSession;

@Controller
public class EmployerController {

    @Autowired
    private EmployerService employerService;
    
    @Autowired
    private EmployerRepository employerRepository;
    
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private HttpSession session;

    private String otpCode;  

    @GetMapping("/employer-register")
	public String showEmployerRegistrationForm(Model model) {
		model.addAttribute("employer", new Employer());
		return "employer-register";
	}
	
    @PostMapping("/registerEmployer")
    public String registerEmployer(@ModelAttribute("employer") Employer employer, @RequestParam("confirmPassword") String confirmPassword, @RequestParam("email") String email, @RequestParam("mobileNumber") String mobileNumber, Model model) {
    	
    	if (employerService.existsByEmailOrMobileNumber(employer.getEmail(), mobileNumber)) {
            model.addAttribute("errorMsg", "Employer already registered with this email or mobile number.");
            return "employer-register";
        }
    	
    	String regex = "^[6-9]\\d{9}$";
    	Pattern pattern = Pattern.compile(regex);
    	Matcher matcher = pattern.matcher(mobileNumber);
    	 if (!matcher.matches()) {
    	        model.addAttribute("errorMsg", "Mobile number must be 10 digits and should start from 6-9");
    	        return "employer-register";
    	    }
        
        if (!employer.getPassword().equals(confirmPassword)) {
            model.addAttribute("errorMsg", "Passwords do not match");
            return "employer-register";
        }
       
        otpCode = emailService.generateOtp();
        System.out.println("sent otp code is:" + otpCode);
        emailService.sendOtpEmail(employer.getEmail(), otpCode);

        model.addAttribute("successMsg", "OTP has been sent to your email. Please enter the OTP to verify.");
        model.addAttribute("employer",  employer);  
        return "otp-verification";  
    }

    @PostMapping("/verifyOtp")
    public String verifyOtp(@RequestParam("enteredOtp") String enteredOtp, @ModelAttribute("employer") Employer employer, Model model) {
        if (enteredOtp.equals(otpCode)) {
            employer.setOtpVerified(true);  
            boolean status = employerService.saveEmployer(employer);
            if (status) {
                model.addAttribute("successMsg", "Employer registered successfully");
                return "employer-login";  
            } else {
                model.addAttribute("errorMsg", "Employer not registered");
                return "employer-register";
            }
        } else {
            model.addAttribute("errorMsg", "Invalid OTP. Please try again.");
            model.addAttribute("employer",  employer);  
            return "otp-verification";
        }
    }
    
    @GetMapping("/employer-login")
    public String showEmployerLoginForm(Model model) {
		model.addAttribute("employer", new Employer());
		return "employer-login";
	}
    

    @PostMapping("/EmployerDashboard")
    public String submitEmployerLoginForm(@ModelAttribute("Employer") Employer employer, Model model, HttpSession session) {
    	Employer validEmployer = employerService.loginEmployer(employer.getEmail(),employer.getPassword());
    	if(validEmployer != null) {
    		session.setAttribute("employer", validEmployer);
    		//return "employer-dashboard";
    		return "redirect:/employer-dashboard?employerId=" + validEmployer.getEmployerId();
    	}
    	else {
    		model.addAttribute("errorMsg","Email id and password are incorrect");
    		return "employer-login";
    	}
    }
    
    
    @GetMapping("/view-candidates/{jobId}")
    public String viewJobApplications(@PathVariable Long jobId, Model model, HttpSession session) {
        Job job = jobRepository.findById(jobId).orElseThrow(() -> new RuntimeException("Job not found"));

        Employer employer = (Employer) session.getAttribute("employer");
        if (employer != null && job.getEmployer().getEmployerId().equals(employer.getEmployerId())) {
        List<Application> jobApplications = applicationRepository.findByJob_JobId(jobId);

        model.addAttribute("job", job);
        model.addAttribute("jobApps", jobApplications);

        return "employerApplications";
        } else {
           
            return "redirect:/employer-login"; 
    }
   }
    
    @PostMapping("/employerlogout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/employer-login";
    }
    

    @GetMapping("/employer-profile")
    public String showEmployerProfilePage(Model model, HttpSession session) {
        
        Employer employer = (Employer) session.getAttribute("employer");

        if (employer != null) {
            model.addAttribute("employer", employer);
            return "employer-profile"; 
        } else {
            return "redirect:/employer-login";  
        }
    }


    @PostMapping("/update-employer-profile")
    public String updateEmployerProfile(@ModelAttribute("employer") Employer employer, Model model, HttpSession session) {
       
        Employer currentEmployer = (Employer) session.getAttribute("employer");

        if (currentEmployer != null) {
           
            currentEmployer.setFullName(employer.getFullName());
            currentEmployer.setMobileNumber(employer.getMobileNumber());
            currentEmployer.setEmail(employer.getEmail());
            
            currentEmployer.setEmployercompanyName(employer.getEmployercompanyName());
            currentEmployer.setEmployercompanyAddress(employer.getEmployercompanyAddress());
            currentEmployer.setEmployercompanyContactNumber(employer.getEmployercompanyContactNumber());
            
            
            employerService.saveEmployer(currentEmployer);
            session.setAttribute("employer", currentEmployer);

            model.addAttribute("successMsg", "Profile updated successfully!");
            return "redirect:/employer-dashboard?employerId=" + currentEmployer.getEmployerId(); 

        } else {
            return "redirect:/employer-login";  
        }
    }
    
}
