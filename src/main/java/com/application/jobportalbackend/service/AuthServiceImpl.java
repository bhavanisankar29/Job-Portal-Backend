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
import com.application.jobportalbackend.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final RecruiterRepository recruiterRepository;
    private final UserRepository userRepository;
    private final JobSeekerRepository jobSeekerRepository;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthServiceImpl(RecruiterRepository recruiterRepository,
                           UserRepository userRepository,
                           JobSeekerRepository jobSeekerRepository,
                           PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager,
                           JwtService jwtService) {
        this.recruiterRepository = recruiterRepository;
        this.userRepository = userRepository;
        this.jobSeekerRepository = jobSeekerRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
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
        String encryptedPassword = passwordEncoder.encode(recruiterSignupDTO.getPassword());
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
        jobSeeker.setPassword(passwordEncoder.encode(jobSeekerSignupDTO.getPassword()));
        jobSeeker.setYearsOfExperience(jobSeekerSignupDTO.getYearsOfExperience());

        jobSeekerRepository.save(jobSeeker);

        User user = new User();
        user.setEmail(jobSeekerSignupDTO.getEmail());
        String encryptedPassword = passwordEncoder.encode(jobSeekerSignupDTO.getPassword());
        user.setPassword(encryptedPassword);
        user.setRole("JOBSEEKER");
        user.setJobSeeker(jobSeeker);

        userRepository.save(user);

        return "JobSeeker Registration Successful!";
    }

    @Override
    public SigninResponseDTO userSignin(SigninRequestDTO signinRequestDTO) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequestDTO.getEmail(),
                signinRequestDTO.getPassword()));

        User user = userRepository.findByEmail(signinRequestDTO.getEmail());

        String token = jwtService.generateToken(user.getEmail(),  user.getRole());

        SigninResponseDTO signinResponseDTO = new SigninResponseDTO();
        signinResponseDTO.setToken(token);
        signinResponseDTO.setUserId(user.getUserId());
        signinResponseDTO.setEmail(user.getEmail());
        signinResponseDTO.setRole(user.getRole());

        if(user.getJobSeeker()!=null) signinResponseDTO.setJobSeekerId(user.getJobSeeker().getJobSeekerId());

        if(user.getRecruiter()!=null) signinResponseDTO.setRecruiterId(user.getRecruiter().getRecruiterId());

        return signinResponseDTO;
    }
}
