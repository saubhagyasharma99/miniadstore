package com.example.adservice.service;

import com.example.adservice.model.Ad;

import java.util.List;

public interface AdService {
    List<Ad> getAllAds();

    Ad getAdById(Long adId);

    Ad saveAd(Ad ad);

    void deleteAd(Long adId);

    List<Ad> findAdsByAgent(Long agentId);

    Ad updateAd(Long adId, Ad ad);

}