package com.example.alumni.service;

import com.example.alumni.dto.AlumniRequest;
import com.example.alumni.dto.AlumniResponse;
import com.example.alumni.entity.AlumniProfile;
import com.example.alumni.repository.AlumniRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AlumniServiceImplTest {

    @Mock
    private PhantomBusterClient phantomBusterClient;

    @Mock
    private AlumniRepository alumniRepository;

    @InjectMocks
    private AlumniServiceImpl alumniService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSearchAndSave() {
        AlumniRequest request = new AlumniRequest();
        request.setUniversity("Test University");
        request.setDesignation("Engineer");
        request.setPassoutYear(2020);

        AlumniResponse response = new AlumniResponse(
                "John Doe", "Engineer", "Test University", "NY", "Tech Lead", 2020
        );

        when(phantomBusterClient.fetchAlumniData(request)).thenReturn(List.of(response));

        List<AlumniResponse> result = alumniService.searchAndSave(request);

        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
        verify(alumniRepository, times(1)).saveAll(anyList());
    }

    @Test
    void testGetAll() {
        AlumniProfile profile = new AlumniProfile();
        profile.setName("Jane Doe");
        profile.setCurrentRole("Analyst");
        profile.setUniversity("Test University");
        profile.setLocation("LA");
        profile.setLinkedinHeadline("Finance Expert");
        profile.setPassoutYear(2019);
        when(alumniRepository.findAll()).thenReturn(List.of(profile));
        List<AlumniResponse> result = alumniService.getAll();
        assertEquals(1, result.size());
        assertEquals("Jane Doe", result.get(0).getName());
        assertEquals("Analyst", result.get(0).getCurrentRole());
    }
}
