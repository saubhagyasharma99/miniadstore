package com.example.adservice.service;
import com.example.adservice.exception.AdNotFoundException;
import com.example.adservice.exception.AgentNotFoundException;
import com.example.adservice.model.Ad;
import com.example.adservice.model.Agent;
import com.example.adservice.repository.AdRepository;
import com.example.adservice.repository.AgentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdServiceImpl implements AdService {
    private final AdRepository adRepository;
    private final AgentRepository agentRepository;

    public AdServiceImpl(AdRepository adRepository, AgentRepository agentRepository) {
        this.adRepository = adRepository;
        this.agentRepository = agentRepository;
    }

    @Override
    public List<Ad> getAllAds() {
        return adRepository.findAll();
    }

    @Override
    public Ad getAdById(Long adId) {
        return adRepository.findByAdId(adId)
                .orElseThrow(() -> new AdNotFoundException("Ad not found with adId: " + adId));
    }

    @Override
    public Ad saveAd(Ad ad) {
        Long agentId = ad.getAgent().getAgentId();
        Agent agent = agentRepository.findById(agentId)
                .orElseThrow(() -> new AgentNotFoundException("Agent not found with agentId: " + agentId));
        ad.setAgent(agent);
        return adRepository.save(ad);
    }

    @Override
    public void deleteAd(Long adId) {
        Ad ad = getAdById(adId);
        adRepository.delete(ad);
    }

    @Override
    public List<Ad> findAdsByAgent(Long agentId) {
        Agent agent = agentRepository.findById(agentId)
                .orElseThrow(() -> new AgentNotFoundException("Agent not found with agentId: " + agentId));
        return adRepository.findByAgent(agent);
    }
    @Override
    public Ad updateAd(Long adId, Ad updatedAd) {
        Ad existingAd = adRepository.findById(adId)
                .orElseThrow(() -> new AdNotFoundException("Ad not found with ID: " + adId));

        existingAd.setCity(updatedAd.getCity());
        existingAd.setCounty(updatedAd.getCounty());
        existingAd.setEircode(updatedAd.getEircode());
        existingAd.setDatePosted(updatedAd.getDatePosted());
        return adRepository.save(existingAd);
    }
}