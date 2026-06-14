package com.application.jobportalbackend.service;

import com.application.jobportalbackend.dto.JobSeekerResponseDTO;
import com.application.jobportalbackend.dto.RecruiterResponseDTO;
import com.application.jobportalbackend.entity.JobSeeker;
import com.application.jobportalbackend.entity.Recruiter;
import com.application.jobportalbackend.entity.User;
import com.application.jobportalbackend.repository.JobSeekerRepository;
import com.application.jobportalbackend.repository.RecruiterRepository;
import com.application.jobportalbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final JobSeekerRepository jobSeekerRepository;
    private final RecruiterRepository recruiterRepository;
    private final UserRepository userRepository;

    public AdminServiceImpl(JobSeekerRepository jobSeekerRepository, RecruiterRepository recruiterRepository, UserRepository userRepository) {
        this.jobSeekerRepository = jobSeekerRepository;
        this.recruiterRepository = recruiterRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<JobSeekerResponseDTO> getAllJobSeekers() {
        List<JobSeekerResponseDTO> jobSeekers = new ArrayList<>();
        List<JobSeeker> jobSeekersList = jobSeekerRepository.findAll();

        jobSeekersList.forEach(jobSeeker -> {
            JobSeekerResponseDTO jobSeekerResponseDTO = new JobSeekerResponseDTO();
            jobSeekerResponseDTO.setJobSeekerId(jobSeeker.getJobSeekerId());
            jobSeekerResponseDTO.setFirstName(jobSeeker.getFirstName());
            jobSeekerResponseDTO.setLastName(jobSeeker.getLastName());
            jobSeekerResponseDTO.setEmail(jobSeeker.getEmail());
            jobSeekers.add(jobSeekerResponseDTO);
        });
        return jobSeekers;
    }

    @Override
    public List<RecruiterResponseDTO>  getAllRecruiters() {
        List<RecruiterResponseDTO> recruiters = new ArrayList<>();
        List<Recruiter> recruitersList = recruiterRepository.findAll();
        recruitersList.forEach(recruiter -> {
            RecruiterResponseDTO recruiterResponseDTO = new RecruiterResponseDTO();
            recruiterResponseDTO.setRecruiterId(recruiter.getRecruiterId());
            recruiterResponseDTO.setFirstName(recruiter.getFirstName());
            recruiterResponseDTO.setLastName(recruiter.getLastName());
            recruiterResponseDTO.setEmail(recruiter.getEmail());
            recruiterResponseDTO.setCompanyName(recruiter.getCompanyName());
            recruiterResponseDTO.setPhoneNo(recruiter.getPhoneNo());
            recruiters.add(recruiterResponseDTO);
        });
        return recruiters;
    }

    @Override
    public String removeJobSeeker(Long jobSeekerId) {

        if(!jobSeekerRepository.existsById(jobSeekerId)) {
            throw new RuntimeException("JobSeeker with id " + jobSeekerId + " does not exist!");
        }
        User user = userRepository.findByJobSeeker_JobSeekerId(jobSeekerId);
        if(user!=null) { userRepository.delete(user); }
        jobSeekerRepository.deleteById(jobSeekerId);
        return "JobSeeker with id " + jobSeekerId + " has been removed.";
    }

    @Override
    public String removeRecruiter(Long recruiterId) {

        if(!recruiterRepository.existsById(recruiterId)) {
            throw new RuntimeException("Recruiter with id " + recruiterId + " does not exist!");
        }
        User user = userRepository.findByRecruiter_RecruiterId(recruiterId);
        if(user!=null) { userRepository.delete(user); }
        recruiterRepository.deleteById(recruiterId);
        return "Recruiter with id " + recruiterId + " has been removed.";
    }
}
