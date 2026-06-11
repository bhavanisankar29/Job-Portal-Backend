package com.application.jobportalbackend.service;

import com.application.jobportalbackend.dto.JobSeekerDTO;
import com.application.jobportalbackend.dto.RecruiterDTO;
import com.application.jobportalbackend.entity.JobSeeker;
import com.application.jobportalbackend.entity.Recruiter;
import com.application.jobportalbackend.repository.RecruiterRepository;

public class AuthServiceImpl implements AuthService {

    private final RecruiterRepository recruiterRepository;

    public AuthServiceImpl(RecruiterRepository recruiterRepository) {
        this.recruiterRepository = recruiterRepository;
    }

    @Override
    public String recruiterRegistration(RecruiterDTO recruiterDTO) {
        // Should implement a method or exception to check the phone no. given

        Recruiter recruiter = new Recruiter();
        recruiter.setEmail(recruiterDTO.getEmail());
        recruiter.setFirstName(recruiterDTO.getFirstName());
        recruiter.setLastName(recruiterDTO.getLastName());
        recruiter.setRecruiterBio(recruiterDTO.getRecruiterBio());
        recruiter.setCompanyName(recruiterDTO.getCompanyName());
        recruiter.setPhoneNo(recruiterDTO.getPhoneNo());

        recruiterRepository.save(recruiter);

        // Should add the recruiter as a user?
        return "Recruiter Registration Successful!";
    }

    @Override
    public String jobSeekerRegistration(JobSeekerDTO jobSeekerDTO) {

        JobSeeker jobSeeker = new JobSeeker();

        jobSeeker.setEmail(jobSeekerDTO.getEmail());
        jobSeeker.setFirstName(jobSeekerDTO.getFirstName());
        jobSeeker.setLastName(jobSeekerDTO.getLastName());
        jobSeeker.setYearsOfExperience(jobSeekerDTO.getYearsOfExperience());

        // Should add the jobSeeker as user?
        return "JobSeeker Registration Successful!";
    }
}
