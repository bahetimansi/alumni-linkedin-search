package com.example.alumni.service;

import com.example.alumni.dto.AlumniRequest;
import com.example.alumni.dto.AlumniResponse;
import com.example.alumni.dto.PaginatedAlumni;

import java.util.List;

public interface AlumniService {

    /**
     * Searches alumni profiles using the PhantomBuster API and saves them to the database.
     *
     * @param request the search request containing university, designation, and optional pass-out year
     * @return list of alumni profile DTOs matching the search
     */
    List<AlumniResponse> searchAndSave(AlumniRequest request);

    /**
     * Retrieves all saved alumni profiles.
     *
     * @return list of all saved alumni profiles
     */
    List<AlumniResponse> getAll();

    /**
     * Retrieves filtered and paginated alumni profiles.
     *
     * @param page         page number (1-based)
     * @param pageSize     number of items per page
     * @param university   optional filter by university name
     * @param designation  optional filter by current role/designation
     * @param passoutYear  optional filter by passout year
     * @param sortBy       field to sort by
     * @param sortOrder    asc or desc
     * @return paginated and sorted list of alumni profile DTOs
     */
    PaginatedAlumni getAllFiltered(int page, int pageSize, String university, String designation,
                                   Integer passoutYear, String sortBy, String sortOrder);

}
