package com.application.jobportalbackend.dto;

import com.application.jobportalbackend.entity.JobType;
import com.application.jobportalbackend.entity.Skill;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
public class JobListDTO {

    private  Long jobId;
    private String companyName;
    private String jobTitle;
    private String jobDescription;
    private LocalDate postedDate;
    private LocalDate deadLineDate;
    private int noOfJobPositions;
    private double salary;
    private JobType jobType;
    private String recruiterName;
    private List<Skill> jobSkills;
}
