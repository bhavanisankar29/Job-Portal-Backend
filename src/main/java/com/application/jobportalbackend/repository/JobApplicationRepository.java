package com.application.jobportalbackend.repository;

import com.application.jobportalbackend.entity.JobApplication;
import com.application.jobportalbackend.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    boolean existsByJobIdAndJobSeekerId(Long jobId, Long jobSeekerId);

    JobApplication findByJobIdAndJobSeekerId(Long jobId, Long jobSeekerId);

    List<JobApplication> findByJobSeekerIdAndStatus(Long jobSeekerId, Status status);
}
