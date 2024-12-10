package com.example.NextU.mapper;

import com.example.NextU.dto.UserDTO;
import com.example.NextU.models.User;

public class UserMapper {
	
	 public static UserDTO toDTO(User user) {
	        UserDTO dto = new UserDTO();
	        dto.setUserId(user.getUserId());
	        dto.setName(user.getName());
	        dto.setEmail(user.getEmail());
	        dto.setPhone(user.getPhone());
	        dto.setTenthScore(user.getTenthScore());
	        dto.setTwelfthScore(user.getTwelfthScore());
	        dto.setGraduationScore(user.getGraduationScore());
	        dto.setPostgraduationScore(user.getPostgraduationScore());
	        dto.setLocation(user.getLocation());
	        dto.setSkills(user.getSkills());
	        dto.setPreviousCompany(user.getPreviousCompany());
	        dto.setExperienceYears(user.getExperienceYears());
	        dto.setExperienceMonths(user.getExperienceMonths());
	        dto.setResumeFileName(user.getResumeFileName());
	        dto.setOtpVerified(user.isOtpVerified());
	       // dto.setCreatedAt(user.getCreatedAt());
	        return dto;
	    }

	    public static User toEntity(UserDTO dto) {
	        User user = new User();
	        user.setUserId(dto.getUserId());
	        user.setName(dto.getName());
	        user.setEmail(dto.getEmail());
	        user.setPhone(dto.getPhone());
	        user.setTenthScore(dto.getTenthScore());
	        user.setTwelfthScore(dto.getTwelfthScore());
	        user.setGraduationScore(dto.getGraduationScore());
	        user.setPostgraduationScore(dto.getPostgraduationScore());
	        user.setLocation(dto.getLocation());
	        user.setSkills(dto.getSkills());
	        user.setPreviousCompany(dto.getPreviousCompany());
	        user.setExperienceYears(dto.getExperienceYears());
	        user.setExperienceMonths(dto.getExperienceMonths());
	        user.setResumeFileName(dto.getResumeFileName());
	        user.setOtpVerified(dto.isOtpVerified());
	        //user.setCreatedAt(dto.getCreatedAt());
	        return user;
	    }

}
