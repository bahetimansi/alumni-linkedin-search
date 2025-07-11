package com.example.alumni.service;

import com.example.alumni.dto.AlumniRequest;
import com.example.alumni.dto.AlumniResponse;
import com.example.alumni.dto.PaginatedAlumni;
import com.example.alumni.entity.AlumniProfile;
import com.example.alumni.repository.AlumniRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlumniServiceImpl implements AlumniService {

    @Autowired
    private PhantomBusterClient phantomBusterClient;

    @Autowired
    private AlumniRepository repository;

    @Override
    public List<AlumniResponse> searchAndSave(AlumniRequest request) {
        List<AlumniResponse> alumniResponses = phantomBusterClient.fetchAlumniData(request);
        List<AlumniProfile> entities = alumniResponses.stream()
                .map(AlumniResponse::toEntity)
                .collect(Collectors.toList());
        repository.saveAll(entities);
        return alumniResponses;
    }

    @Override
    public List<AlumniResponse> getAll() {
        return repository.findAll().stream()
                .map(AlumniResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public PaginatedAlumni getAllFiltered(int page, int pageSize, String university, String designation,
                                          Integer passoutYear, String sortBy, String sortOrder) {
        Sort.Direction direction = Sort.Direction.fromString(sortOrder);
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by(direction, sortBy));
        Page<AlumniProfile> alumniPage = repository.findAll(pageable);
        List<AlumniResponse> filtered = alumniPage.getContent().stream()
                .filter(profile ->
                        (university == null || profile.getUniversity().equalsIgnoreCase(university)) &&
                                (designation == null || profile.getCurrentRole().equalsIgnoreCase(designation)) &&
                                (passoutYear == null || (profile.getPassoutYear() != null && profile.getPassoutYear().equals(passoutYear)))
                )
                .map(AlumniResponse::new)
                .collect(Collectors.toList());
        PaginatedAlumni result = new PaginatedAlumni();
        result.setAlumni(filtered);
        result.setCurrentPage(alumniPage.getNumber() + 1);
        result.setTotalPages(alumniPage.getTotalPages());
        result.setTotalItems(alumniPage.getTotalElements());
        return result;
    }
}
