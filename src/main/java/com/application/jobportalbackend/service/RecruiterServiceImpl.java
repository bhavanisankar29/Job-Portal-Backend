package com.application.jobportalbackend.service;

import com.application.jobportalbackend.dto.JobListDTO;
import com.application.jobportalbackend.entity.Recruiter;
import com.application.jobportalbackend.repository.RecruiterRepository;
import com.application.jobportalbackend.repository.SkillRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecruiterServiceImpl implements RecruiterService {

    private final RecruiterRepository recruiterRepository;
    private final SkillRepository skillRepository;
    private final ModelMapper modelMapper;

    public RecruiterServiceImpl(RecruiterRepository recruiterRepository, SkillRepository skillRepository) {
        this.recruiterRepository = recruiterRepository;
        this.skillRepository = skillRepository;
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
}
