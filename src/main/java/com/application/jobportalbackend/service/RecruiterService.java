package com.application.jobportalbackend.service;

import com.application.jobportalbackend.dto.JobListDTO;
import com.application.jobportalbackend.dto.JobPostRequestDTO;
import com.application.jobportalbackend.dto.JobUpdateRequestDTO;

import java.util.List;
import java.util.Map;

public interface RecruiterService {

    List<JobListDTO> getAllJobsPosted(Long recruiterId);
    Map<String, Object> deleteJob(Long jobId, Long recruiterId);
    String postJob(JobPostRequestDTO jobPostRequestDTO);
    String updateJob(Long recruiterId, Long jobId, JobUpdateRequestDTO jobUpdateRequestDTO);
}
