package com.example.adservice;

import com.example.adservice.model.Ad;
import com.example.adservice.repository.AdRepository;
import com.example.adservice.repository.AgentRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AdServiceIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AdRepository adRepository;

    @Autowired
    private AgentRepository agentRepository;

    @Test
    public void testGetAllAds() throws Exception {
        Ad ad1 = new Ad();
        ad1.setAdId(1L);
        ad1.setPrice(100.0);
        ad1.setCounty("County");
        ad1.setCity("City");
        ad1.setEircode("Eircode");
        ad1.setDatePosted(LocalDate.now());

        Ad ad2 = new Ad();
        ad2.setAdId(2L);
        ad2.setPrice(200.0);
        ad2.setCounty("County");
        ad2.setCity("City");
        ad2.setEircode("Eircode");
        ad2.setDatePosted(LocalDate.now());

        adRepository.save(ad1);
        adRepository.save(ad2);

        mockMvc.perform(get("/ads")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
}