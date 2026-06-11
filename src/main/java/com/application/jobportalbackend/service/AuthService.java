package com.application.jobportalbackend.service;

import com.application.jobportalbackend.dto.JobSeekerDTO;
import com.application.jobportalbackend.dto.RecruiterDTO;

public interface AuthService {
    String recruiterRegistration(RecruiterDTO recruiterDTO);
    String jobSeekerRegistration(JobSeekerDTO jobSeekerDTO);
}
