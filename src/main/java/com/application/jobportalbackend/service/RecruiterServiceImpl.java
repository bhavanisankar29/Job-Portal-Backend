package com.application.jobportalbackend.service;

import com.application.jobportalbackend.dto.*;
import com.application.jobportalbackend.entity.*;
import com.application.jobportalbackend.repository.JobRepository;
import com.application.jobportalbackend.repository.JobSeekerRepository;
import com.application.jobportalbackend.repository.RecruiterRepository;
import com.application.jobportalbackend.repository.SkillRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecruiterServiceImpl implements RecruiterService {

    private final RecruiterRepository recruiterRepository;
    private final SkillRepository skillRepository;
    private final JobSeekerRepository jobSeekerRepository;
    private final JobRepository jobRepository;

    public RecruiterServiceImpl(RecruiterRepository recruiterRepository, SkillRepository skillRepository, JobRepository jobRepository, JobSeekerRepository jobSeekerRepository) {
        this.recruiterRepository = recruiterRepository;
        this.skillRepository = skillRepository;
        this.jobSeekerRepository = jobSeekerRepository;
        this.jobRepository = jobRepository;

    }

    @Override
    public List<JobListDTO> getAllJobsPosted(Long recruiterId) {

        Recruiter recruiter = recruiterRepository.findById(recruiterId)
                .orElseThrow(()-> new RuntimeException("Recruiter with id " + recruiterId + " not found"));

        List<JobListDTO> jobListDTOS = new ArrayList<>();

        recruiter.getJobListings().forEach(job -> {
            JobListDTO jobListDTO = new JobListDTO();
            jobListDTO.setJobId(job.getJobId());
            jobListDTO.setCompanyName(recruiter.getCompanyName());
            jobListDTO.setJobTitle(job.getJobTitle());
            jobListDTO.setJobDescription(job.getJobDescription());
            jobListDTO.setPostedDate(job.getPostedDate());
            jobListDTO.setDeadLineDate(job.getDeadLineDate());
            jobListDTO.setNoOfJobPositions(job.getNoOfJobPositions());
            jobListDTO.setSalary(job.getSalary());
            jobListDTO.setJobType(job.getJobType());
            jobListDTO.setRecruiterName(recruiter.getFirstName()+" "+recruiter.getLastName());
            List<String> skills = skillRepository.findByJobId(job.getJobId());
            jobListDTO.setJobSkills(skills);
            jobListDTOS.add(jobListDTO);
        });
        return jobListDTOS;
    }

    @Override
    public Map<String,Object> deleteJob(Long jobId, Long recruiterId) {

        Job job = jobRepository.findById(jobId)
                .orElseThrow(()-> new RuntimeException("Job with id " + jobId + " not found"));

        Map<String, Object> map = new HashMap<>();
        if(!job.getPostedBy().getRecruiterId().equals(recruiterId)) {
            map.put("success", false);
            map.put("message", "RecruiterId mismatch for the job.");
            return map;
        }
        jobRepository.delete(job);
        map.put("success", true);
        map.put("message", "Job with id " + jobId + " has been deleted.");
        return map;
    }

    @Override
    public String postJob(JobPostRequestDTO jobPostRequestDTO) {

        if(!recruiterRepository.existsById(jobPostRequestDTO.getRecruiterId())
                && jobPostRequestDTO.getSkillIds().isEmpty()) {
            return "Job not posted!";
        }
        Job job = new Job();

        job.setJobTitle(jobPostRequestDTO.getJobTitle());
        job.setJobDescription(jobPostRequestDTO.getJobDescription());
        job.setSalary(jobPostRequestDTO.getSalary());
        job.setJobType(jobPostRequestDTO.getJobType());
        job.setDeadLineDate(jobPostRequestDTO.getDeadline());
        job.setNoOfJobPositions(jobPostRequestDTO.getNoOfPositions());
        job.setPostedDate(LocalDate.now());

        job.setPostedBy(
                recruiterRepository.findById(
                        jobPostRequestDTO.getRecruiterId()
                ).orElseThrow()
        );

        job.setSkills(
                skillRepository.findAllById(
                        jobPostRequestDTO.getSkillIds()
                )
        );

        jobRepository.save(job);
        return "Job Posted Successfully!";
    }

    @Override
    public String updateJob(Long recruiterId, Long jobId, JobUpdateRequestDTO jobUpdateRequestDTO) {

        if(!recruiterRepository.existsById(recruiterId) ||
                !jobRepository.existsById(jobId) ||
                !jobUpdateRequestDTO.getRecruiterId().equals(recruiterId)) {
            return "Job not Updated!";
        }

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found with id " + jobId));

        job.setJobDescription(jobUpdateRequestDTO.getJobDescription());
        job.setJobTitle(jobUpdateRequestDTO.getJobTitle());
        job.setDeadLineDate(jobUpdateRequestDTO.getDeadline());
        job.setJobType(jobUpdateRequestDTO.getJobType());
        job.setNoOfJobPositions(jobUpdateRequestDTO.getNoOfPositions());
        job.setSalary(jobUpdateRequestDTO.getSalary());
        List<Skill> skills = skillRepository.findAllById(jobUpdateRequestDTO.getSkillIds());
        job.setSkills(skills);
        jobRepository.save(job);
        return "Job updated successfully.";
    }

    @Override
    public String updateApplicationStatus(Long recruiterId, Long jobId, Long jobSeekerId, JobStatusUpdateDTO jobStatusUpdateDTO) {

        if(!recruiterRepository.existsById(recruiterId) || !jobRepository.existsById(jobId) || !jobRepository.existsById(jobSeekerId)) {
            return "Job Status not updated!";
        }
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found with id " + jobId));

        if(!job.getPostedBy().getRecruiterId().equals(recruiterId)) {
            return "Job Status not updated! It was not posted by the recruiter with id " + recruiterId;
        }
        JobSeeker jobSeeker = jobSeekerRepository.findById(jobSeekerId)
                .orElseThrow(() -> new RuntimeException("Job seeker with id " + jobSeekerId + " not found"));

        List<JobApplication> jobApplicationList = jobSeeker.getJobApplicationList();
        for(JobApplication jobApplication : jobApplicationList) {
            if(jobApplication.getJob().getJobId().equals(jobId)) {
                jobApplication.setStatus(jobStatusUpdateDTO.getJobStatus());
            }
        }
        jobSeekerRepository.save(jobSeeker);
        return "Job Status updated successfully.";
    }

    @Override
    public List<JobApplicationListDTO> getJobApplications(Long jobId, Long recruiterId) {

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found with id " + jobId));

        if(!job.getPostedBy().getRecruiterId().equals(recruiterId)) {
            throw new RuntimeException("Recruiter with id " + recruiterId + " not authorized to view the job applications.");
        }

        List<JobApplicationListDTO> allApplications = new ArrayList<>();
        List<JobApplication> jobApplicationList = job.getApplications();

        jobApplicationList.forEach(jobApplication -> {
            JobApplicationListDTO jobApplicationListDTO = new JobApplicationListDTO();
            jobApplicationListDTO.setJobSeekerId(jobApplication.getJobSeeker().getJobSeekerId());
            jobApplicationListDTO.setFirstName(jobApplication.getJobSeeker().getFirstName());
            jobApplicationListDTO.setLastName(jobApplication.getJobSeeker().getLastName());
            jobApplicationListDTO.setResumeUrl(jobApplication.getResumeUrl());
            jobApplicationListDTO.setAppliedDate(jobApplication.getAppliedDate());
            jobApplicationListDTO.setStatus(jobApplication.getStatus());
            List<Skill> jobSeekerSkills = skillRepository.findAllByJobSeekerId(jobApplication.getJobSeeker().getJobSeekerId());

            List<SkillDTO> skillDTOList = new ArrayList<>();
            for(Skill skill: jobSeekerSkills) {
                SkillDTO skillDTO = new SkillDTO();
                skillDTO.setSkillId(skill.getSkillId());
                skillDTO.setSkillName(skill.getSkillName());
                skillDTO.setDescription(skill.getSkillDescription());
            }
            jobApplicationListDTO.setSkills(skillDTOList);
            allApplications.add(jobApplicationListDTO);
        });
        return allApplications;
    }
}
