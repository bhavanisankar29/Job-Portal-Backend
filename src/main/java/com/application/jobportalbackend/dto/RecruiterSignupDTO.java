package com.application.jobportalbackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RecruiterSignupDTO {

    private String email;
    private String firstName;
    private String lastName;
    private String phoneNo;
    private String recruiterBio;
    private String companyName;
    private String password;
    private String role;

}
