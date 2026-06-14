package com.application.jobportalbackend.dto;

import com.application.jobportalbackend.entity.JobType;
import com.application.jobportalbackend.entity.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class JobApplicationResponseDTO {

    private Long applicationId;
    private Long jobId;

    private String jobTitle;
    private String jobDescription;
    private Status jobStatus;
    private JobType jobType;

    private LocalDate postedDate;
    private LocalDate appliedDate;
    private LocalDate deadLineDate;

    private int noOfPositions;
    private double salary;
}
