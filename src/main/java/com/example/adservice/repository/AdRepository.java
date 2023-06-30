package com.example.adservice.repository;

import com.example.adservice.model.Ad;
import com.example.adservice.model.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {
    Optional<Ad> findByAdId(Long adId);

    List<Ad> findByAgent(Agent agent);
}
