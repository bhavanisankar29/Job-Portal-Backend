package com.application.jobportalbackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SigninResponseDTO {

    private String token;
    private Long userId;
    private String email;
    private String role;
    private Long jobSeekerId;
    private Long recruiterId;
}
