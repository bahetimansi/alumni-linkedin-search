package com.example.alumni.controller;

import com.example.alumni.dto.AlumniRequest;
import com.example.alumni.dto.AlumniResponse;
import com.example.alumni.service.AlumniService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/alumni")
public class AlumniController {

    @Autowired
    private AlumniService alumniService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> search(@Valid @RequestBody AlumniRequest request) {
        List<AlumniResponse> result = alumniService.searchAndSave(request);
        return ResponseEntity.ok(Map.of("status", "success", "data", result));
    }

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAll() {
        List<AlumniResponse> result = alumniService.getAll();
        return ResponseEntity.ok(Map.of("status", "success", "data", result));
    }

    @GetMapping("/filtered")
    public ResponseEntity<Map<String, Object>> getAllFiltered(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String university,
            @RequestParam(required = false) String designation,
            @RequestParam(required = false) Integer passoutYear,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder) {

        var result = alumniService.getAllFiltered(page, pageSize, university, designation, passoutYear, sortBy, sortOrder);
        return ResponseEntity.ok(Map.of("status", "success", "data", result));
    }
}
