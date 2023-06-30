package com.example.adservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adId;

    private Double price;
    private String county;
    private String city;
    private String eircode;
    private LocalDate datePosted;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "agent_id")
    private Agent agent;


    public Ad(long adId, double price, String county, String city, String eircode, LocalDate datePosted) {
        this.adId = adId;
        this.price = price;
        this.county= county;
        this.city=city;
        this.eircode=eircode;
        this.datePosted=datePosted;

    }
}