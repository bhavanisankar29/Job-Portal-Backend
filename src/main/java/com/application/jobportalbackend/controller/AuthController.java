package com.application.jobportalbackend.controller;

import com.application.jobportalbackend.dto.JobSeekerSignupDTO;
import com.application.jobportalbackend.dto.RecruiterSignupDTO;
import com.application.jobportalbackend.dto.SigninRequestDTO;
import com.application.jobportalbackend.dto.SigninResponseDTO;
import com.application.jobportalbackend.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Recruiter Sign-up")
    @PostMapping("/recruiter/signup")
    public ResponseEntity<String> recruiterRegistration(@RequestBody RecruiterSignupDTO recruiterSignupDTO) {
        return new ResponseEntity<String>(authService.recruiterRegistration(recruiterSignupDTO), HttpStatus.OK);
    }

    @Operation(summary = "JobSeeker Sign-up")
    @PostMapping("/jobSeeker/signup")
    public ResponseEntity<String> jobSeekerRegistration(@RequestBody JobSeekerSignupDTO jobSeekerSignupDTO) {
        return new ResponseEntity<String>(authService.jobSeekerRegistration(jobSeekerSignupDTO), HttpStatus.OK);
    }

    @Operation(summary = "User Sign-in")
    @PostMapping("/user/signin")
    public SigninResponseDTO userSignin(@RequestBody SigninRequestDTO signinRequestDTO) {
        return authService.userSignin(signinRequestDTO);
    }
}
