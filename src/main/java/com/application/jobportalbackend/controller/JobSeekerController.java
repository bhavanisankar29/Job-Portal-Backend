package com.application.jobportalbackend.controller;

import com.application.jobportalbackend.dto.ApplyJobDTO;
import com.application.jobportalbackend.dto.JobApplicationResponseDTO;
import com.application.jobportalbackend.dto.JobListDTO;
import com.application.jobportalbackend.service.JobSeekerService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobSeekers")
public class JobSeekerController {

    private final JobSeekerService jobSeekerService;

    public JobSeekerController(JobSeekerService jobSeekerService) {
        this.jobSeekerService = jobSeekerService;
    }

    @PostMapping("/applyJob/{jobId}/{jobSeekerId}")
    public String applyJob(@PathVariable Long jobId,
                           @PathVariable Long jobSeekerId,
                           @Valid @RequestBody ApplyJobDTO applyJobDTO) {
        return jobSeekerService.applyJob(jobId, jobSeekerId, applyJobDTO);
    }

    @DeleteMapping("/withdrawApplication/{jobId}/{jobSeekerId}")
    public String withdrawApplication(@PathVariable Long jobId,
                                      @PathVariable Long jobSeekerId) {
        return jobSeekerService.withdrawApplication(jobId, jobSeekerId);
    }

    @GetMapping("/allJobs")
    public List<JobListDTO> getAllJobs() {
        return jobSeekerService.getAllJobs();
    }

    @GetMapping("/allAppliedJobs/{jobSeekerId}")
    public List<JobApplicationResponseDTO> getAllAppliedJobs(@PathVariable Long jobSeekerId) {
        return jobSeekerService.getAllAppliedJobs(jobSeekerId);
    }
}
