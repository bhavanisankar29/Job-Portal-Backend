package com.application.jobportalbackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class JobSeekerResponseDTO {

    private Long jobSeekerId;
    private String email;
    private String firstName;
    private String lastName;
}
