package com.application.jobportalbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "recruiters")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Recruiter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruiter_id")
    private Long recruiterId;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String phoneNo;

    @Column(nullable = false)
    private String recruiterBio;

    @Column(nullable = false)
    private String companyName;

    @OneToMany(mappedBy = "postedBy",fetch =FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true )
    private List<Job> jobListings;

}
