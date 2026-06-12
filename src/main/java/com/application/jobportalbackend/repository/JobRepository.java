package com.application.jobportalbackend.repository;

import com.application.jobportalbackend.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
}
