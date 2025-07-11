package com.example.alumni.service;

import com.example.alumni.dto.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Service
public class PhantomBusterClient {

    @Value("${phantombuster.api.key}")
    private String apiKey;

    @Value("${phantombuster.agent.id}")
    private String agentId;

    @Value("${phantombuster.base.url}")
    private String baseUrl;

    private final WebClient webClient;

    public PhantomBusterClient(WebClient.Builder builder) {
        this.webClient = builder.build();
    }

    public List<AlumniResponse> fetchAlumniData(AlumniRequest request) {
        String searchQuery = request.getDesignation() + " " + request.getUniversity();
        if (request.getPassoutYear() != null) {
            searchQuery += " " + request.getPassoutYear();
        }

        Map<String, Object> launchPayload = Map.of(
                "id", agentId,
                "arguments", Map.of("search", searchQuery),
                "saveArguments", true
        );

        Map<String, Object> launchResponse = webClient.post()
                .uri(baseUrl + "/agents/launch")
                .header("X-Phantombuster-Key-1", apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(launchPayload)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        Map<String, Object> container = (Map<String, Object>) launchResponse.get("container");
        String resultUrl = (String) container.get("resultObjectUrl");

        try { Thread.sleep(10000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

        return webClient.get()
                .uri(resultUrl)
                .retrieve()
                .bodyToFlux(LinkedInProfileDTO.class)
                .map(profile -> new AlumniResponse(
                        profile.getName(),
                        profile.getCurrentRole(),
                        request.getUniversity(),
                        profile.getLocation(),
                        profile.getLinkedinHeadline(),
                        request.getPassoutYear()
                ))
                .collectList()
                .block();
    }
}
