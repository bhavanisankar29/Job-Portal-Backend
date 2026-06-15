package com.application.jobportalbackend.repository;

import com.application.jobportalbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByJobSeeker_JobSeekerId(Long jobSeekerId);
    User findByRecruiter_RecruiterId(Long recruiterId);
}
