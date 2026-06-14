package com.application.jobportalbackend.service;

import com.application.jobportalbackend.dto.ApplyJobDTO;
import com.application.jobportalbackend.dto.JobApplicationResponseDTO;
import com.application.jobportalbackend.dto.JobListDTO;
import com.application.jobportalbackend.entity.*;
import com.application.jobportalbackend.repository.JobApplicationRepository;
import com.application.jobportalbackend.repository.JobRepository;
import com.application.jobportalbackend.repository.JobSeekerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class JobSeekerServiceImpl implements JobSeekerService {

    private final JobSeekerRepository jobSeekerRepository;
    private final JobRepository jobRepository;
    private final JobApplicationRepository jobApplicationRepository;

    public JobSeekerServiceImpl(JobSeekerRepository jobSeekerRepository,
                                JobRepository jobRepository,
                                JobApplicationRepository jobApplicationRepository) {
        this.jobSeekerRepository = jobSeekerRepository;
        this.jobRepository = jobRepository;
        this.jobApplicationRepository = jobApplicationRepository;
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

        if(applyJobDTO.getResumeUrl() == null ||
                applyJobDTO.getResumeUrl().isBlank()) {
            throw new RuntimeException("Resume URL is required");
        }

        jobApplication.setResumeUrl(applyJobDTO.getResumeUrl());

        jobApplicationRepository.save(jobApplication);
        return "Successfully applied for this Job!";
    }

    @Override
    public String withdrawApplication(Long jobId, Long jobSeekerId) {

        JobApplication jobApplication = jobApplicationRepository.findByJobIdAndJobSeekerId(jobId, jobSeekerId);

        if(jobApplication == null) { return "Application not found!"; }

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

            convertJobTOJobListDTO(job, jobListDTO);

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

            convertJobApplicationToJobApplicationResponseDTO(jobApplication, jobApplicationResponseDTO);

            jobApplicationResponseDTOS.add(jobApplicationResponseDTO);
        });
        return jobApplicationResponseDTOS;
    }

    @Override
    public List<JobApplicationResponseDTO> getJobsWithGivenStatus(Status status, Long jobSeekerId) {

        List<JobApplication> jobApplicationList = jobApplicationRepository.findByJobSeekerIdAndStatus(jobSeekerId, status);

        if(jobApplicationList == null) {
            throw new RuntimeException("There are no applications with the given jobSeekerId" + jobSeekerId + " and status " + status);
        }
        List<JobApplicationResponseDTO> jobApplicationResponseDTOS = new ArrayList<>();

        jobApplicationList.forEach(jobApplication -> {
            JobApplicationResponseDTO jobApplicationResponseDTO = new JobApplicationResponseDTO();

            convertJobApplicationToJobApplicationResponseDTO(jobApplication, jobApplicationResponseDTO);

            jobApplicationResponseDTOS.add(jobApplicationResponseDTO);
        });
        return jobApplicationResponseDTOS;
    }

    @Override
    public JobListDTO getJob(Long jobId) {

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job with " + jobId + " not found!"));

        JobListDTO jobListDTO = new JobListDTO();

        convertJobTOJobListDTO(job, jobListDTO);

        return jobListDTO;
    }

    private void convertJobTOJobListDTO(Job job, JobListDTO jobListDTO) {

        jobListDTO.setJobId(job.getJobId()); // jobId
        jobListDTO.setCompanyName(job.getPostedBy().getCompanyName()); // companyName
        jobListDTO.setJobTitle(job.getJobTitle()); // jobTitle
        jobListDTO.setJobDescription(job.getJobDescription()); // jobDescription
        jobListDTO.setPostedDate(job.getPostedDate()); // postedDate
        jobListDTO.setDeadLineDate(job.getDeadLineDate()); // deadLineDate
        jobListDTO.setNoOfJobPositions(job.getNoOfJobPositions()); // noOfPositions
        jobListDTO.setSalary(job.getSalary()); // salary
        jobListDTO.setJobType(job.getJobType()); // jobType
        jobListDTO.setRecruiterName(job.getPostedBy().getFirstName() + " " +  job.getPostedBy().getLastName()); // recruiterName
        List<Skill> skills = job.getSkills();
        jobListDTO.setJobSkills(skills); // skills

    }

    private void convertJobApplicationToJobApplicationResponseDTO(JobApplication jobApplication,
                                                                  JobApplicationResponseDTO jobApplicationResponseDTO) {

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

    }
}
