package com.application.jobportalbackend.repository;

import com.application.jobportalbackend.entity.JobApplication;
import com.application.jobportalbackend.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    boolean existsByJob_JobIdAndJobSeeker_JobSeekerId(Long jobId, Long jobSeekerId);

    JobApplication findByJob_JobIdAndJobSeeker_JobSeekerId(Long jobId, Long jobSeekerId);

    List<JobApplication> findByJobSeeker_JobSeekerIdAndStatus(Long jobSeekerId, Status status);
}
