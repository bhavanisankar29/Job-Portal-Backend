package com.application.jobportalbackend.service;

import com.application.jobportalbackend.dto.JobSeekerSignupDTO;
import com.application.jobportalbackend.dto.RecruiterSignupDTO;
import com.application.jobportalbackend.dto.SigninRequestDTO;
import com.application.jobportalbackend.dto.SigninResponseDTO;
import com.application.jobportalbackend.entity.JobSeeker;
import com.application.jobportalbackend.entity.Recruiter;
import com.application.jobportalbackend.entity.User;
import com.application.jobportalbackend.repository.JobSeekerRepository;
import com.application.jobportalbackend.repository.RecruiterRepository;
import com.application.jobportalbackend.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final RecruiterRepository recruiterRepository;
    private final UserRepository userRepository;
    private final JobSeekerRepository jobSeekerRepository;

    public AuthServiceImpl(RecruiterRepository recruiterRepository, UserRepository userRepository, JobSeekerRepository jobSeekerRepository) {
        this.recruiterRepository = recruiterRepository;
        this.userRepository = userRepository;
        this.jobSeekerRepository = jobSeekerRepository;
    }

    @Override
    public String recruiterRegistration(RecruiterSignupDTO recruiterSignupDTO) {
        // Should implement a method or exception to check the phone no. given

        Recruiter recruiter = new Recruiter();
        recruiter.setEmail(recruiterSignupDTO.getEmail());
        recruiter.setFirstName(recruiterSignupDTO.getFirstName());
        recruiter.setLastName(recruiterSignupDTO.getLastName());
        recruiter.setRecruiterBio(recruiterSignupDTO.getRecruiterBio());
        recruiter.setCompanyName(recruiterSignupDTO.getCompanyName());
        recruiter.setPhoneNo(recruiterSignupDTO.getPhoneNo());

        recruiterRepository.save(recruiter);

        User user = new User();
        user.setEmail(recruiterSignupDTO.getEmail());
        String encryptedPassword = new BCryptPasswordEncoder().encode(recruiterSignupDTO.getPassword());
        user.setPassword(encryptedPassword);
        user.setRole(recruiterSignupDTO.getRole());
        user.setRecruiter(recruiter);

        userRepository.save(user);

        return "Recruiter Registration Successful!";
    }

    @Override
    public String jobSeekerRegistration(JobSeekerSignupDTO jobSeekerSignupDTO) {

        JobSeeker jobSeeker = new JobSeeker();

        jobSeeker.setEmail(jobSeekerSignupDTO.getEmail());
        jobSeeker.setFirstName(jobSeekerSignupDTO.getFirstName());
        jobSeeker.setLastName(jobSeekerSignupDTO.getLastName());
        jobSeeker.setPassword(new BCryptPasswordEncoder().encode(jobSeekerSignupDTO.getPassword()));
        jobSeeker.setYearsOfExperience(jobSeekerSignupDTO.getYearsOfExperience());

        jobSeekerRepository.save(jobSeeker);

        User user = new User();
        user.setEmail(jobSeekerSignupDTO.getEmail());
        String encryptedPassword = new BCryptPasswordEncoder().encode(jobSeekerSignupDTO.getPassword());
        user.setPassword(encryptedPassword);
        user.setRole("JOBSEEKER");
        user.setJobSeeker(jobSeeker);

        userRepository.save(user);

        return "JobSeeker Registration Successful!";
    }

    @Override
    public SigninResponseDTO userSignin(SigninRequestDTO signinRequestDTO) {

        User user = userRepository.findByEmail(signinRequestDTO.getEmail());
        if (user == null) {
            throw new RuntimeException("User not found! Invalid email."); // Should create a separate exception for this.
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean passwordMatch = passwordEncoder.matches(signinRequestDTO.getPassword(), user.getPassword());
        if (!passwordMatch) { throw new RuntimeException("Invalid password."); }

        SigninResponseDTO signinResponseDTO = new SigninResponseDTO();
        signinResponseDTO.setUserId(user.getUserId());
        signinResponseDTO.setEmail(user.getEmail());
        if(user.getRole().equals("JOBSEEKER")) {
            signinResponseDTO.setJobSeekerId(user.getJobSeeker().getJobSeekerId());
        } else if(user.getRole().equals("RECRUITER")) {
            signinResponseDTO.setRecruiterId(user.getRecruiter().getRecruiterId());
        }
        return signinResponseDTO;
    }
}
