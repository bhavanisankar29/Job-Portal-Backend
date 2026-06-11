package com.application.jobportalbackend.controller;

import com.application.jobportalbackend.dto.JobSeekerDTO;
import com.application.jobportalbackend.dto.RecruiterDTO;
import com.application.jobportalbackend.service.AuthService;
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

    @PostMapping("/recruiter/signup")
    public ResponseEntity<String> recruiterRegistration(@RequestBody RecruiterDTO recruiterDTO) {
        return new ResponseEntity<String>(authService.recruiterRegistration(recruiterDTO), HttpStatus.OK);
    }

    @PostMapping("/jobSeeker/signup")
    public ResponseEntity<String> jobSeekerRegistration(@RequestBody JobSeekerDTO jobSeekerDTO) {
        return new ResponseEntity<String>(authService.jobSeekerRegistration(jobSeekerDTO), HttpStatus.OK);
    }
}
