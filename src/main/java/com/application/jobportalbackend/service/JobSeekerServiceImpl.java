package com.application.jobportalbackend.service;

import com.application.jobportalbackend.dto.ApplyJobDTO;
import com.application.jobportalbackend.dto.JobApplicationResponseDTO;
import com.application.jobportalbackend.dto.JobListDTO;
import com.application.jobportalbackend.entity.Job;
import com.application.jobportalbackend.entity.JobApplication;
import com.application.jobportalbackend.entity.JobSeeker;
import com.application.jobportalbackend.entity.Status;
import com.application.jobportalbackend.repository.JobApplicationRepository;
import com.application.jobportalbackend.repository.JobRepository;
import com.application.jobportalbackend.repository.JobSeekerRepository;
import com.application.jobportalbackend.repository.SkillRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class JobSeekerServiceImpl implements JobSeekerService {

    private final JobSeekerRepository jobSeekerRepository;
    private final JobRepository jobRepository;
    private final JobApplicationRepository jobApplicationRepository;
    private final SkillRepository skillRepository;

    public JobSeekerServiceImpl(JobSeekerRepository jobSeekerRepository, JobRepository jobRepository, JobApplicationRepository jobApplicationRepository, SkillRepository skillRepository) {
        this.jobSeekerRepository = jobSeekerRepository;
        this.jobRepository = jobRepository;
        this.jobApplicationRepository = jobApplicationRepository;
        this.skillRepository = skillRepository;
    }

    @Override
    public String applyJob(Long jobId, Long jobSeekerId, ApplyJobDTO applyJobDTO) {

        JobSeeker jobSeeker = jobSeekerRepository.findById(jobSeekerId)
                .orElseThrow(() -> new RuntimeException("jobSeekerId not found"));

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("job not found"));

        if(jobApplicationRepository.existsByJobIdAndJobSeekerId(jobId, jobSeekerId)) {
            return "Already applied for this Job!";
        }

        if(job.getDeadLineDate().isBefore(LocalDate.now())) {
            return "Application window for this job has been closed!";
        }

        JobApplication jobApplication = new JobApplication();
        jobApplication.setJob(job);
        jobApplication.setJobSeeker(jobSeeker);
        jobApplication.setAppliedDate(LocalDate.now());
        jobApplication.setStatus(Status.PENDING);
        jobApplication.setResumeUrl(applyJobDTO.getResumeUrl());

        jobApplicationRepository.save(jobApplication);
        return "Successfully applied for this Job!";
    }

    @Override
    public String withdrawApplication(Long jobId, Long jobSeekerId) {

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("job not found"));

        JobSeeker jobSeeker = jobSeekerRepository.findById(jobSeekerId)
                .orElseThrow(() -> new RuntimeException("jobSeekerId not found"));

        if(!jobApplicationRepository.existsByJobIdAndJobSeekerId(jobId, jobSeekerId)) {
            return "JobSeeker with id " + jobSeekerId + " did not apply for the Job!";
        }
        JobApplication jobApplication = jobApplicationRepository.findByJobIdAndJobSeekerId(jobId, jobSeekerId);
        if(jobApplication.getStatus() == Status.PENDING) {
            jobApplicationRepository.delete(jobApplication);
            return "Application withdrawn successfully!";
        }
        return "Accepted or Rejected application can not be withdrawn!";
    }

    @Override
    public List<JobListDTO> getAllJobs() {

        List<Job> jobs = jobRepository.findAll();
        List<JobListDTO> jobListDTOS = new ArrayList<>();

        jobs.forEach(job -> {
            JobListDTO jobListDTO = new JobListDTO();
            jobListDTO.setJobId(job.getJobId());
            jobListDTO.setCompanyName(job.getPostedBy().getCompanyName());
            jobListDTO.setJobTitle(job.getJobTitle());
            jobListDTO.setJobDescription(job.getJobDescription());
            jobListDTO.setPostedDate(job.getPostedDate());
            jobListDTO.setDeadLineDate(job.getDeadLineDate());
            jobListDTO.setNoOfJobPositions(job.getNoOfJobPositions());
            jobListDTO.setSalary(job.getSalary());
            jobListDTO.setJobType(job.getJobType());
            jobListDTO.setRecruiterName(job.getPostedBy().getFirstName() + " " + job.getPostedBy().getLastName());
            List<String> skills = skillRepository.findByJobId(job.getJobId());
            jobListDTO.setJobSkills(skills);
            jobListDTOS.add(jobListDTO);
        });
        return jobListDTOS;
    }

    @Override
    public List<JobApplicationResponseDTO> getAllAppliedJobs(Long jobSeekerId) {

        JobSeeker jobSeeker = jobSeekerRepository.findById(jobSeekerId)
                .orElseThrow(() -> new RuntimeException("jobSeekerId not found"));

        List<JobApplication> jobApplicationList = jobSeeker.getJobApplicationList();
        List<JobApplicationResponseDTO> jobApplicationResponseDTOS = new ArrayList<>();

        jobApplicationList.forEach(jobApplication -> {

            JobApplicationResponseDTO jobApplicationResponseDTO = new JobApplicationResponseDTO();

            jobApplicationResponseDTO.setApplicationId(jobApplication.getApplicationId()); // applicationId
            jobApplicationResponseDTO.setJobId(jobApplication.getJob().getJobId()); // jobId
            jobApplicationResponseDTO.setJobTitle(jobApplication.getJob().getJobTitle()); // jobTitle
            jobApplicationResponseDTO.setJobDescription(jobApplication.getJob().getJobDescription()); // jobDescription
            jobApplicationResponseDTO.setJobStatus(jobApplication.getStatus()); // jobStatus
            jobApplicationResponseDTO.setJobType(jobApplication.getJob().getJobType()); // jobType
            jobApplicationResponseDTO.setPostedDate(jobApplication.getJob().getPostedDate()); // postedDate
            jobApplicationResponseDTO.setAppliedDate(jobApplication.getAppliedDate()); // appliedDate
            jobApplicationResponseDTO.setDeadLineDate(jobApplication.getJob().getDeadLineDate()); // deadLineDate
            jobApplicationResponseDTO.setNoOfPositions(jobApplication.getJob().getNoOfJobPositions()); // noOfPositions
            jobApplicationResponseDTO.setSalary(jobApplication.getJob().getSalary()); // salary

            jobApplicationResponseDTOS.add(jobApplicationResponseDTO);
        });
        return jobApplicationResponseDTOS;
    }
}
