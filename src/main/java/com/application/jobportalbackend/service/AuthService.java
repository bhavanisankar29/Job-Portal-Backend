package com.application.jobportalbackend.service;

import com.application.jobportalbackend.dto.JobSeekerSignupDTO;
import com.application.jobportalbackend.dto.RecruiterSignupDTO;
import com.application.jobportalbackend.dto.SigninRequestDTO;
import com.application.jobportalbackend.dto.SigninResponseDTO;

public interface AuthService {
    String recruiterRegistration(RecruiterSignupDTO recruiterSignupDTO);
    String jobSeekerRegistration(JobSeekerSignupDTO jobSeekerSignupDTO);
    SigninResponseDTO userSignin(SigninRequestDTO signinRequestDTO);
}
