package com.example.alumni.dto;

import lombok.Data;
import java.util.List;

@Data
public class PaginatedAlumni {

    private List<AlumniResponse> alumni;
    private int currentPage;
    private int totalPages;
    private long totalItems;
}

