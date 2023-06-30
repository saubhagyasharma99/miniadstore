package com.example.adservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer {
    @Id
    private String email;
    private String name;
    private LocalDate dateOfBirth;
    private String occupation;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "customer_ad",
            joinColumns = @JoinColumn(name = "customer_email"),
            inverseJoinColumns = @JoinColumn(name = "ad_id"))
    private List<Ad> ads = new ArrayList<>();

}