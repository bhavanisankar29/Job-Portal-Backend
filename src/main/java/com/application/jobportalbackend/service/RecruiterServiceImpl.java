package com.application.jobportalbackend.service;

import com.application.jobportalbackend.dto.JobListDTO;
import com.application.jobportalbackend.dto.JobPostRequestDTO;
import com.application.jobportalbackend.dto.JobUpdateRequestDTO;
import com.application.jobportalbackend.entity.Job;
import com.application.jobportalbackend.entity.Recruiter;
import com.application.jobportalbackend.entity.Skill;
import com.application.jobportalbackend.repository.JobRepository;
import com.application.jobportalbackend.repository.RecruiterRepository;
import com.application.jobportalbackend.repository.SkillRepository;
import org.modelmapper.ModelMapper;
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
    private final JobRepository jobRepository;
    private final ModelMapper modelMapper;

    public RecruiterServiceImpl(RecruiterRepository recruiterRepository, SkillRepository skillRepository, JobRepository jobRepository) {
        this.recruiterRepository = recruiterRepository;
        this.skillRepository = skillRepository;
        this.jobRepository = jobRepository;
        this.modelMapper = new ModelMapper();

    }

    @Override
    public List<JobListDTO> getAllJobsPosted(Long recruiterId) {

        Recruiter recruiter = recruiterRepository.findById(recruiterId)
                .orElseThrow(()-> new RuntimeException("Recruiter with id " + recruiterId + " not found"));

        List<JobListDTO> jobListDTOS = new ArrayList<>();

        recruiter.getJobListings().forEach(job -> {
            JobListDTO jobListDTO = modelMapper.map(job, JobListDTO.class);
            jobListDTO.setRecruiterName(recruiter.getFirstName()+" "+recruiter.getLastName());
            List<String> skills = skillRepository.findByJobId(job.getJobId());
            jobListDTO.setJobSkills_string(skills);
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
        Job job = modelMapper.map(jobPostRequestDTO, Job.class);
        job.setPostedBy(recruiterRepository.findById(jobPostRequestDTO.getRecruiterId()).orElse(null));
        List<Skill> skills = skillRepository.findAllById(jobPostRequestDTO.getSkillIds());
        job.setSkills(skills);
        job.setPostedDate(LocalDate.now());

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
        job.setDeadLineDate(jobUpdateRequestDTO.getDeadline());
        job.setJobType(jobUpdateRequestDTO.getJobType());
        job.setNoOfJobPositions(jobUpdateRequestDTO.getNoOfPositions());
        job.setSalary(jobUpdateRequestDTO.getSalary());
        List<Skill> skills = skillRepository.findAllById(jobUpdateRequestDTO.getSkillIds());
        job.setSkills(skills);
        jobRepository.save(job);
        return "Job updated successfully.";
    }
}
