package com.example.alumni.controller;

import com.example.alumni.dto.AlumniRequest;
import com.example.alumni.dto.AlumniResponse;
import com.example.alumni.service.AlumniService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AlumniController.class)
public class AlumniControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlumniService alumniService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testSearchApi() throws Exception {
        AlumniRequest request = new AlumniRequest();
        request.setUniversity("Test University");
        request.setDesignation("Developer");
        request.setPassoutYear(2020);

        AlumniResponse mockResponse = new AlumniResponse(
                "John Smith", "Developer", "Test University", "New York", "Java Engineer", 2020
        );

        Mockito.when(alumniService.searchAndSave(any(AlumniRequest.class)))
                .thenReturn(List.of(mockResponse));

        mockMvc.perform(post("/api/v1/alumni")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data[0].name").value("John Smith"))
                .andExpect(jsonPath("$.data[0].university").value("Test University"));
    }
}
