package com.application.jobportalbackend.repository;

import com.application.jobportalbackend.entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    boolean existsByJobIdAndJobSeekerId(Long jobId, Long jobSeekerId);

    JobApplication findByJobIdAndJobSeekerId(Long jobId, Long jobSeekerId);
}
