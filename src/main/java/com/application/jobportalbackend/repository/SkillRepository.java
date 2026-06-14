package com.application.jobportalbackend.repository;

import com.application.jobportalbackend.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SkillRepository extends JpaRepository<Skill, Long> {

    @Query("SELECT s FROM Skill s JOIN s.jobsList jl WHERE jl.jobId = :jobId")
    List<Skill> findByJobId(Long jobId);

    @Query("SELECT s FROM Skill s JOIN s.jobSeekerList js WHERE js.jobSeekerId = :jobSeekerId")
    List<Skill> findAllByJobSeekerId(Long jobSeekerId);
}
