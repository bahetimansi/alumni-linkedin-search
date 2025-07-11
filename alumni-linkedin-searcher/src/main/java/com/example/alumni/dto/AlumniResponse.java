package com.example.alumni.dto;

import com.example.alumni.entity.AlumniProfile;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlumniResponse {

    private String name;
    private String currentRole;
    private String university;
    private String location;
    private String linkedinHeadline;
    private Integer passoutYear;

    public AlumniResponse(AlumniProfile entity) {
        this.name = entity.getName();
        this.currentRole = entity.getCurrentRole();
        this.university = entity.getUniversity();
        this.location = entity.getLocation();
        this.linkedinHeadline = entity.getLinkedinHeadline();
        this.passoutYear = entity.getPassoutYear();
    }

    public AlumniProfile toEntity() {
        AlumniProfile profile = new AlumniProfile();
        profile.setName(this.name);
        profile.setCurrentRole(this.currentRole);
        profile.setUniversity(this.university);
        profile.setLocation(this.location);
        profile.setLinkedinHeadline(this.linkedinHeadline);
        profile.setPassoutYear(this.passoutYear);
        return profile;
    }
}
