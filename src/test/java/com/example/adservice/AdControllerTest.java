package com.example.adservice;

import com.example.adservice.controller.AdController;
import com.example.adservice.exception.AgentNotFoundException;
import com.example.adservice.model.Ad;
import com.example.adservice.model.Agent;
import com.example.adservice.repository.AgentRepository;
import com.example.adservice.service.AdService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AdControllerTest {
    @Mock
    private AdService adService;

    @Mock
    private AgentRepository agentRepository;

    @InjectMocks
    private AdController adController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllAds() {
        List<Ad> ads = new ArrayList<>();
        when(adService.getAllAds()).thenReturn(ads);

        List<Ad> result = adController.getAllAds();

        assertEquals(ads, result);
        verify(adService, times(1)).getAllAds();
    }

    @Test
    void testGetAdById() {
        Long adId = 1L;
        Ad ad = new Ad();
        when(adService.getAdById(adId)).thenReturn(ad);

        Ad result = adController.getAdById(adId);

        assertEquals(ad, result);
        verify(adService, times(1)).getAdById(adId);
    }

    @Test
    void testCreateAd() {
        Long agentId = 1L;
        Agent agent = new Agent();
        Ad ad = new Ad();
        ad.setAgent(agent);

        when(agentRepository.findById(agentId)).thenReturn(Optional.of(agent));
        when(adService.saveAd(ad)).thenReturn(ad);

        Ad result = adController.createAd(ad);

        assertEquals(ad, result);
        verify(agentRepository, times(1)).findById(agentId);
        verify(adService, times(1)).saveAd(ad);
    }

    @Test
    void testCreateAdThrowsAgentNotFoundException() {
        Long agentId = 1L;
        Ad ad = new Ad();
        ad.setAgent(new Agent());

        when(agentRepository.findById(agentId)).thenReturn(Optional.empty());

        try {
            adController.createAd(ad);
        } catch (AgentNotFoundException e) {
            assertEquals("Agent not found with agentId: " + agentId, e.getMessage());
        }

        verify(agentRepository, times(1)).findById(agentId);
        verify(adService, never()).saveAd(ad);
    }

    @Test
    void testDeleteAd() {
        Long adId = 1L;
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Ad deleted successfully");

        doNothing().when(adService).deleteAd(adId);

        ResponseEntity<String> result = adController.deleteAd(adId);

        assertEquals(expectedResponse, result);
        verify(adService, times(1)).deleteAd(adId);
    }

    @Test
    void testFindAdsByAgent() {
        Long agentId = 1L;
        List<Ad> ads = new ArrayList<>();

        when(adService.findAdsByAgent(agentId)).thenReturn(ads);

        List<Ad> result = adController.findAdsByAgent(agentId);

        assertEquals(ads, result);
        verify(adService, times(1)).findAdsByAgent(agentId);
    }
}