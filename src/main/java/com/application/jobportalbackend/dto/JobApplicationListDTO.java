package com.application.jobportalbackend.dto;

import com.application.jobportalbackend.entity.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
public class JobApplicationListDTO {

    private Long jobSeekerId;
    private String firstName;
    private String lastName;
    private List<SkillDTO> skills;
    private String resumeUrl;
    private LocalDate appliedDate;
    private Status status;

}
