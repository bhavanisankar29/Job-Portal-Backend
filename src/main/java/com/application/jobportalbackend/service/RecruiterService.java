package com.application.jobportalbackend.service;

import com.application.jobportalbackend.dto.JobListDTO;

import java.util.List;

public interface RecruiterService {

    List<JobListDTO> getAllJobsPosted(Long recruiterId);
}
