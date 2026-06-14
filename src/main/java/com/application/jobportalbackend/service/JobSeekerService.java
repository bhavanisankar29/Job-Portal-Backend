package com.application.jobportalbackend.service;

import com.application.jobportalbackend.dto.ApplyJobDTO;
import com.application.jobportalbackend.dto.JobApplicationResponseDTO;
import com.application.jobportalbackend.dto.JobListDTO;

import java.util.List;

public interface JobSeekerService {
    String applyJob(Long jobId, Long jobSeekerId, ApplyJobDTO applyJobDTO);
    String withdrawApplication(Long jobId, Long jobSeekerId);
    List<JobListDTO> getAllJobs();
    List<JobApplicationResponseDTO> getAllAppliedJobs(Long jobSeekerId);
}
