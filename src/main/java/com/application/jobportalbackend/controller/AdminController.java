package com.application.jobportalbackend.controller;

import com.application.jobportalbackend.dto.JobSeekerResponseDTO;
import com.application.jobportalbackend.dto.RecruiterResponseDTO;
import com.application.jobportalbackend.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @Operation(summary = "Get All JobSeekers Details")
    @GetMapping("/getAllJobSeekers")
    public List<JobSeekerResponseDTO> getAllJobSeekers() {
        return adminService.getAllJobSeekers();
    }

    @Operation(summary = "Get All Recruiters Details")
    @GetMapping("/getAllRecruiters")
    public List<RecruiterResponseDTO> getAllRecruiters() {
        return adminService.getAllRecruiters();
    }

    @Operation(summary = "Remove a JobSeeker Profile")
    @DeleteMapping("/removeProfile/jobSeeker/{jobSeekerId}")
    public String removeJobSeeker(@PathVariable Long jobSeekerId) {
        return adminService.removeJobSeeker(jobSeekerId);
    }

    @Operation(summary = "Remove a Recruiter Profile")
    @DeleteMapping("/removeProfile/recruiter/{recruiterId}")
    public String removeRecruiter(@PathVariable Long recruiterId) {
        return adminService.removeRecruiter(recruiterId);
    }
}
