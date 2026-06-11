package com.application.jobportalbackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class JobSeekerDTO {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String yearsOfExperience;
}
