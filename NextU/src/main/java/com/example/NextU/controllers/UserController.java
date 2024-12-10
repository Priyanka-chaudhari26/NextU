package com.example.NextU.controllers;

import com.example.NextU.dto.UserDTO;
import com.example.NextU.mapper.UserMapper;
import com.example.NextU.models.Application;
import com.example.NextU.models.Employer;
import com.example.NextU.models.Job;
import com.example.NextU.models.User;
import com.example.NextU.repositories.UserRepository;
import com.example.NextU.services.UserService;
import com.example.NextU.session.UserSession;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.env.Environment;

import com.example.NextU.services.ApplicationService;
import com.example.NextU.services.EmailService;
import com.example.NextU.services.JobService;

import java.io.File;
import java.net.http.HttpHeaders;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


//@RestController
@Controller
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private JobService jobService;
    
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private ApplicationService applicationService;
    
    @Autowired 
    private UserRepository userRepository;
    
    @Autowired
    private Environment env;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserSession userSession;

    
    private String otpCode; 
    
    @GetMapping("/jobseeker-register")
    public String showRegistrationForm(Model model) {
    	model.addAttribute("user", new User());
        return "jobseeker-register"; 
    }
    
    @GetMapping("/jobseeker-login")
    public String showLoginForm(Model model) {
    	model.addAttribute("user", new User());
        return "jobseeker-login"; 
    }


    @PostMapping(value = "/jobseeker-register", consumes = "multipart/form-data")
    public String registerUser(@RequestParam("name") String name, @RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("confirmPassword") String confirmPassword, @RequestParam("phone") String phone, @RequestParam("tenthScore") double tenthScore, @RequestParam("twelfthScore") double twelfthScore, @RequestParam(value = "graduationScore", required = false) Double graduationScore, @RequestParam(value = "postgraduationScore", required = false) Double postgraduationScore, @RequestParam("location") String location, @RequestParam(value= "skills", required = false) List<String> skills, @RequestParam(value = "previousCompany") String previousCompany,  @RequestParam(value = "experienceYears") Integer experienceYears,  @RequestParam(value = "experienceMonths") Integer experienceMonths,  @RequestParam(value="resume", required = false) MultipartFile resume, Model model) {
    	try {
        	if (!password.equals(confirmPassword)) {
                model.addAttribute("errorMsg", "Passwords do not match.");
                return "jobseeker-register"; // Return to the registration form with an error message
            }
        	
            User user = new User();
            

            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);
            user.setPhone(phone);
            user.setSkills(skills);
            user.setTenthScore(tenthScore);
            user.setTwelfthScore(twelfthScore);
            user.setGraduationScore(graduationScore);
            user.setPostgraduationScore(postgraduationScore);
            user.setLocation(location);
            user.setPreviousCompany(previousCompany);
            user.setExperienceYears(experienceYears);
            user.setExperienceMonths(experienceMonths);
            
            System.out.println("PrevComp"+ previousCompany + "ey" + experienceYears + "pm" + experienceMonths);
            System.out.println(user.getPreviousCompany());
            System.out.println(user.getExperienceYears());
            System.out.println(user.getExperienceMonths());
            
            
            if (resume != null && !resume.isEmpty()) {
            	String resumeFileName = resume.getOriginalFilename();
                int maxFileSize = 2 * 1024 * 1024; 
                if (resume.getSize() > maxFileSize) {
                    
                	model.addAttribute("errorMsg", "Please upload a file smaller than 2MB.");
                    return "jobseeker-register"; 
                }
                uploadResume(user, resume.getBytes());
                user.setResumeFileName(resumeFileName);
            } 

            otpCode = emailService.generateOtp(); 
            emailService.sendOtpEmail(user.getEmail(), otpCode); 
            model.addAttribute("user", user); 
            model.addAttribute("successMessage", "An OTP has been sent to your email. Please verify your account.");
            System.out.println("otp sent");
            System.out.println(otpCode);
            return "userotp-verification"; 
        } catch (Exception e) {
            model.addAttribute("errorMsg", "Registration failed: " + e.getMessage());
            System.out.println("otp cannot sent");
            return "jobseeker-register";
        }

            
    }
    
    @PostMapping("/userverifyOtp")
    public String verifyOtp(@RequestParam("enteredOtp") String enteredOtp, @ModelAttribute("user") User user, Model model) {
        
    	System.out.println(otpCode);
		if (enteredOtp.equals(otpCode)){
            user.setOtpVerified(true);  
            boolean status = userService.registerUser(user);
            if (status) {
                model.addAttribute("successMsg", "User registered successfully");
                return "jobseeker-login";  
            } else {
                model.addAttribute("errorMsg", "User not registered");
                return "jobseeker-register";
            }
        } else {
            model.addAttribute("errorMsg", "Invalid OTP. Please try again.");
            model.addAttribute("user",  user);  
            return "userotp-verification";
        }
    }
    
	  public void uploadResume(User user, byte[] resumeData) throws Exception {
	  user.setResume(resumeData);
	 
	}
    

    @GetMapping("/test-update")
    public String testUpdateUser() {
        Optional<User> optionalUser = userService.findById(1L); // Retrieve a test user by ID
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPreviousCompany("Test Company");
            user.setExperienceYears(5);
            user.setExperienceMonths(6);

            userService.registerUser(user); // Save the updated user
            System.out.println( "User updated successfully!");
            return "testpage";
        } else {
        	System.out.println( "User not found!");
            return "testpage";
        }
    }
    
    @PostMapping("/login")
    public String submitUserLoginForm(@ModelAttribute("User") User user, Model model, HttpSession session) {
    	System.out.println("Attempting login for: " + user.getEmail());
        User validUser = userService.loginUser(user.getEmail(), user.getPassword());
        if (validUser != null) {
        	System.out.println("Login successful for: " + validUser.getEmail());
            session.setAttribute("user", validUser); // Set the user object in the session
            return "redirect:/user-dashboard";
        } else {
        	System.out.println("Login failed for: " + user.getEmail());
            model.addAttribute("errorMsg", "Email id and password are incorrect");
            return "jobseeker-login";
        }
    }
  
    @GetMapping("/user-dashboard")
    public String getDashboard(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            
            List<Job> jobs = jobService.getAllJobs();
            List<Application> appliedJobs = applicationService.getApplicationsByUser(user);
            
         
            Set<Long> appliedJobIds = appliedJobs.stream()
                    .map(application -> application.getJob().getJobId())
                    .collect(Collectors.toSet());

        
            List<Job> availableJobs = jobs.stream()
                    .filter(job -> !appliedJobIds.contains(job.getJobId()))
                    .collect(Collectors.toList());

            model.addAttribute("user", user);
            model.addAttribute("jobs", availableJobs);
            model.addAttribute("userApplications", appliedJobs);
        } else {
            
            return "redirect:/jobseeker-login";
        }

        return "user-dashboard";
    }
    
    @PostMapping("/userlogout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/jobseeker-login";
    }
    
    @GetMapping("/user-profile")
    public String viewUserProfile(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
     
        List<String> registeredSkills = userService.getRegisteredSkills();
        model.addAttribute("registeredSkills", registeredSkills);
        

        if (user != null && user.getResume() != null) {
            model.addAttribute("resumeFile", user.getResume());
            model.addAttribute("resumeFileName", user.getResumeFileName()); 
            System.out.println("Resume File name:" + user.getResumeFileName());
        }

        if (user != null && user.getSkills() != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                String skillsJson = mapper.writeValueAsString(user.getSkills());
        System.out.println("Skills JSON: " + skillsJson); 
                model.addAttribute("skillsJson", skillsJson);
            } catch (Exception e) {
                model.addAttribute("skillsJson", "[]");
        e.printStackTrace();
            }
        } else {
            model.addAttribute("skillsJson", "[]");
            
        }
        return "user-profile";
    }

    @PostMapping("/delete-resume")
    public String deleteResume(HttpSession session) {
        User user = (User) session.getAttribute("user");
        user.setResume(null); 
        userService.updateUser(user); 
        return "redirect:/user-profile";
    }


    @PostMapping(value = "/update-user-profile", consumes = "multipart/form-data")
    public String updateUserProfile(
            @ModelAttribute User user,
            @RequestParam("skills") String skillsString,
            @RequestParam("resume") MultipartFile resume,
            HttpSession session,
            Model model) throws Exception {

        List<String> skills = Arrays.asList(skillsString.split(","));
        
        user.setSkills(skills);

        if (!resume.isEmpty()) {
           
            int maxFileSize = 2 * 1024 * 1024; 
            if (resume.getSize() > maxFileSize) {
                model.addAttribute("errorMessage", "Please upload a file smaller than 2MB.");
                return "jobseeker-register"; 
            }

            uploadResume(user, resume.getBytes());
            user.setResumeFileName(resume.getOriginalFilename());
        }
        userService.updateUser(user);
        session.setAttribute("user", user);

        return "redirect:/user-profile";
    }

    
    @GetMapping("/download-resume/{userId}")
    public ResponseEntity<byte[]> downloadResume(@PathVariable("userId") Long userId) {
       
        User user = userService.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        byte[] resume = user.getResume(); 
        if (resume != null && resume.length > 0) {
            
            return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=\"resume.pdf\"") 
                .body(resume);
        } else {
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    
    @GetMapping("/user/profile/{id}")
    public String viewUserProfile(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id)
                      .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + id));
        model.addAttribute("user", user);
        return "userDetails"; 
    }

    
}