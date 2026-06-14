package com.application.jobportalbackend.service;

import com.application.jobportalbackend.dto.*;

import java.util.List;
import java.util.Map;

public interface RecruiterService {

    List<JobListDTO> getAllJobsPosted(Long recruiterId);
    Map<String, Object> deleteJob(Long jobId, Long recruiterId);
    String postJob(JobPostRequestDTO jobPostRequestDTO,  Long recruiterId);
    String updateJob(Long recruiterId, Long jobId, JobUpdateRequestDTO jobUpdateRequestDTO);
    String updateApplicationStatus(Long recruiterId, Long jobId, Long jobSeekerId, JobStatusUpdateDTO jobStatusUpdateDTO);
    List<JobApplicationListDTO> getJobApplications(Long jobId, Long recruiterId);
}
