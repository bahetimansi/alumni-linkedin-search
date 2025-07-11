package com.example.alumni.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AlumniRequest {

    @NotBlank
    private String university;

    @NotBlank
    private String designation;

    private Integer passoutYear;
}
