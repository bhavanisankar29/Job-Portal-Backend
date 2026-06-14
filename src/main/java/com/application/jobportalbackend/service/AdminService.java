package com.application.jobportalbackend.service;

import com.application.jobportalbackend.dto.JobSeekerResponseDTO;
import com.application.jobportalbackend.dto.RecruiterResponseDTO;

import java.util.List;

public interface AdminService {
    List<JobSeekerResponseDTO> getAllJobSeekers();
    List<RecruiterResponseDTO> getAllRecruiters();
    String removeJobSeeker(Long jobSeekerId);
    String removeRecruiter(Long recruiterId);
}
