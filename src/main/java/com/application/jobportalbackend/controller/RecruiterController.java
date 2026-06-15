package com.application.jobportalbackend.controller;

import com.application.jobportalbackend.dto.JobApplicationListDTO;
import com.application.jobportalbackend.dto.JobPostRequestDTO;
import com.application.jobportalbackend.dto.JobStatusUpdateDTO;
import com.application.jobportalbackend.dto.JobUpdateRequestDTO;
import com.application.jobportalbackend.service.RecruiterService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/recruiters")
public class RecruiterController {

    private final RecruiterService recruiterService;

    public RecruiterController(RecruiterService recruiterService) {
        this.recruiterService = recruiterService;
    }

    @Operation(summary = "Get all Jobs posted by a Recruiter")
    @GetMapping("/allJobsPosted/{recruiterId}")
    public ResponseEntity<?> getAllJobsPosted(@PathVariable Long recruiterId) {
        return new ResponseEntity<>(recruiterService.getAllJobsPosted(recruiterId), HttpStatus.OK);
    }

    @Operation(summary = "Delete a posted Job")
    @DeleteMapping("/{recruiterId}/deleteJob/{jobId}")
    public ResponseEntity<?> deleteJob(@PathVariable Long recruiterId, @PathVariable Long jobId) {
        Map<String,Object> map = recruiterService.deleteJob(jobId, recruiterId);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @Operation(summary = "Post a new Job")
    @PostMapping("/postJob/{recruiterId}")
    public String postJob(@Valid @RequestBody JobPostRequestDTO jobPostRequestDTO,
                          @PathVariable Long recruiterId) {
        return recruiterService.postJob(jobPostRequestDTO, recruiterId);
    }

    @Operation(summary = "Update the details of a posted Job")
    @PutMapping("/updateJob/{recruiterId}/{jobId}")
    public String updateJob(@PathVariable Long recruiterId,
                            @PathVariable Long jobId,
                            @Valid @RequestBody JobUpdateRequestDTO jobUpdateRequestDTO) {
        return recruiterService.updateJob(recruiterId, jobId, jobUpdateRequestDTO);
    }

    @Operation(summary = "Update application status of a JobSeeker")
    @PutMapping("/updateStatus/{recruiterId}/{jobId}/{jobSeekerId}")
    public String updateApplicationStatus(@PathVariable Long recruiterId,
                                          @PathVariable Long jobId,
                                          @PathVariable Long jobSeekerId,
                                          @Valid @RequestBody JobStatusUpdateDTO jobStatusUpdateDTO) {
        return recruiterService.updateApplicationStatus(recruiterId, jobId, jobSeekerId, jobStatusUpdateDTO);
    }

    @Operation(summary = "Get all applications for a Job")
    @GetMapping("{recruiterId}/{jobId}/allApplications")
    public ResponseEntity<List<JobApplicationListDTO>> getAllApplications(@PathVariable Long recruiterId,
                                                                          @PathVariable Long jobId) {
        List<JobApplicationListDTO> jobApplicationListDTOS = recruiterService.getJobApplications(jobId, recruiterId);
        return ResponseEntity.ok(jobApplicationListDTOS);
    }

    //Update recruiter profile
}
