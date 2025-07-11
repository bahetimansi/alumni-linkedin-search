package com.example.alumni.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "alumni_profiles")
public class AlumniProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "role")
    private String currentRole;

    @Column(name = "university")
    private String university;

    @Column(name = "location")
    private String location;

    @Column(name = "linkedin")
    private String linkedinHeadline;

    @Column(name = "passout_year")
    private Integer passoutYear;
}
