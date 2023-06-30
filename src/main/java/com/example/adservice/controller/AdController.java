package com.example.adservice.controller;

import com.example.adservice.exception.AgentNotFoundException;
import com.example.adservice.model.Ad;
import com.example.adservice.model.Agent;
import com.example.adservice.repository.AgentRepository;
import com.example.adservice.service.AdService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ads")
public class AdController {
    private final AdService adService;
    private final AgentRepository agentRepository;

    public AdController(AdService adService, AgentRepository agentRepository) {
        this.adService = adService;
        this.agentRepository = agentRepository;
    }

    @GetMapping
    public List<Ad> getAllAds() {
        return adService.getAllAds();
    }

    @GetMapping("/{adId}")
    public Ad getAdById(@PathVariable Long adId) {
        return adService.getAdById(adId);
    }

    @PostMapping
    public Ad createAd(@RequestBody Ad ad) {
        Long agentId = ad.getAgent().getAgentId();
        Agent agent = agentRepository.findById(agentId)
                .orElseThrow(() -> new AgentNotFoundException("Agent not found with agentId: " + agentId));
        ad.setAgent(agent);
        return adService.saveAd(ad);
    }

    @DeleteMapping("/{adId}")
    public ResponseEntity<String> deleteAd(@PathVariable Long adId) {
        adService.deleteAd(adId);
        return ResponseEntity.ok("Ad deleted successfully");
    }

    @GetMapping("/agent/{agentId}")
    public List<Ad> findAdsByAgent(@PathVariable Long agentId) {
        return adService.findAdsByAgent(agentId);
    }
}