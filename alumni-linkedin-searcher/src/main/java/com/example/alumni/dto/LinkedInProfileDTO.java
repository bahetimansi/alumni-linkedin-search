package com.example.alumni.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LinkedInProfileDTO {

    private String name;
    private String currentRole;
    private String location;
    private String linkedinHeadline;

    @JsonProperty("passout Year")
    private Integer passoutYear;

}
