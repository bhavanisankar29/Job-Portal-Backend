package com.application.jobportalbackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SigninRequestDTO {
    private String email;
    private String password;
}
