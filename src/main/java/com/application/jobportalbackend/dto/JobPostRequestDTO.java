package com.application.jobportalbackend.dto;

import com.application.jobportalbackend.entity.JobType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
public class JobPostRequestDTO {

    private Long recruiterId;
    private String jobTitle;
    private String jobDescription;
    private JobType jobType;
    private List<Long> skillIds;
    private double salary;
    private LocalDate deadline;
    private int noOfPositions;
}
