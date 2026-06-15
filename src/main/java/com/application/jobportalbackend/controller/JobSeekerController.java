package com.application.jobportalbackend.controller;

import com.application.jobportalbackend.dto.ApplyJobDTO;
import com.application.jobportalbackend.dto.JobApplicationResponseDTO;
import com.application.jobportalbackend.dto.JobListDTO;
import com.application.jobportalbackend.entity.Status;
import com.application.jobportalbackend.service.JobSeekerService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Apply for a Job")
    @PostMapping("/applyJob/{jobId}/{jobSeekerId}")
    public String applyJob(@PathVariable Long jobId,
                           @PathVariable Long jobSeekerId,
                           @Valid @RequestBody ApplyJobDTO applyJobDTO) {
        return jobSeekerService.applyJob(jobId, jobSeekerId, applyJobDTO);
    }

    @Operation(summary = "Withdraw Application for a Job")
    @DeleteMapping("/withdrawApplication/{jobId}/{jobSeekerId}")
    public String withdrawApplication(@PathVariable Long jobId,
                                      @PathVariable Long jobSeekerId) {
        return jobSeekerService.withdrawApplication(jobId, jobSeekerId);
    }

    @Operation(summary = "Get all Jobs posted")
    @GetMapping("/allJobs")
    public List<JobListDTO> getAllJobs() {
        return jobSeekerService.getAllJobs();
    }

    @Operation(summary = "Get all Applied Jobs")
    @GetMapping("/allAppliedJobs/{jobSeekerId}")
    public List<JobApplicationResponseDTO> getAllAppliedJobs(@PathVariable Long jobSeekerId) {
        return jobSeekerService.getAllAppliedJobs(jobSeekerId);
    }

    @Operation(summary = "Get all Applied Jobs with given Status")
    @GetMapping("/{jobSeekerId}/jobStatus/{status}")
    public List<JobApplicationResponseDTO> getJobsWithGivenStatus(@PathVariable Status status,
                                                                  @PathVariable Long jobSeekerId) {
        return jobSeekerService.getJobsWithGivenStatus(status, jobSeekerId);
    }

    @Operation(summary = "Get details of a particular Job")
    @GetMapping("/job/{jobId}")
    public JobListDTO getJob(@PathVariable Long jobId) {
        return jobSeekerService.getJob(jobId);
    }
}
