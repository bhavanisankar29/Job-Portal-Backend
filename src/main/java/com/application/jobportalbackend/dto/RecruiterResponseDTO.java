package com.application.jobportalbackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RecruiterResponseDTO {

    private Long recruiterId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;
    private String companyName;
}
