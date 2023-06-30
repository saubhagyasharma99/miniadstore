package com.example.adservice;

import com.example.adservice.exception.AdNotFoundException;
import com.example.adservice.model.Ad;
import com.example.adservice.repository.AdRepository;
import com.example.adservice.service.AdService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class AdServiceTest {

    @Mock
    private AdRepository adRepository;

    @InjectMocks
    private AdService adService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllAds_shouldReturnAllAds() {
        List<Ad> ads = new ArrayList<>();
        ads.add(new Ad(1L, 200.2, "Dublin", "Dublin", "NDF1", LocalDate. of(2015, 12, 31)));
        ads.add(new Ad(2L, 260.2, "Louth", "Louth", "NDA1", LocalDate. of(2016, 12, 31)));

        when(adRepository.findAll()).thenReturn(ads);

        List<Ad> result = adService.getAllAds();

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Dublin", result.get(0).getCity());
        Assertions.assertEquals("Louth", result.get(1).getCity());

        verify(adRepository, times(1)).findAll();
    }

    @Test
    void getAdById_shouldReturnExistingAd() {
        List<Ad> ads = new ArrayList<>();
        long adId = 1L;
        ads.add(new Ad(1L, 200.2, "Dublin", "Dublin", "NDF1", LocalDate. of(2015, 12, 31)));

        Ad result = adService.getAdById(adId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("Dublin", result.getCity());

        verify(adRepository, times(1)).findById(adId);
    }

    @Test
    void getAdById_shouldThrowExceptionForNonExistingAd() {
        long adId = 1L;

        when(adRepository.findById(adId)).thenReturn(Optional.empty());

        Assertions.assertThrows(AdNotFoundException.class, () -> {
            adService.getAdById(adId);
        });

        verify(adRepository, times(1)).findById(adId);
    }

    @Test
    void createAd_shouldCreateNewAd() {
        Ad ad = new Ad(1L, 200.2, "Dublin", "Dublin", "NDF1", LocalDate. of(2015, 12, 31));

        when(adRepository.save(ad)).thenReturn(ad);

        Ad result = adService.saveAd(ad);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("Dublin", result.getCity());

        verify(adRepository, times(1)).save(ad);
    }

    @Test
    void updateAd_shouldUpdateExistingAd() {
        long adId = 1L;
        Ad ad = new Ad(1L, 200.2, "Dublin", "Dublin", "NDF1", LocalDate. of(2015, 12, 31));

        when(adRepository.existsById(adId)).thenReturn(true);
        when(adRepository.save(ad)).thenReturn(ad);

        Ad result = adService.updateAd(adId, ad);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("Dublin", result.getCity());

        verify(adRepository, times(1)).existsById(adId);
        verify(adRepository, times(1)).save(ad);
    }

    @Test
    void updateAd_shouldThrowExceptionForNonExistingAd() {
        long adId = 1L;
        Ad ad = new Ad(1L, 200.2, "Dublin", "Dublin", "NDF1", LocalDate. of(2015, 12, 31));

        when(adRepository.existsById(adId)).thenReturn(false);

        Assertions.assertThrows(AdNotFoundException.class, () -> {
            adService.updateAd(adId, ad);

        });

        verify(adRepository, times(1)).existsById(adId);
        verify(adRepository, never()).save(any(Ad.class));
    }

    @Test
    void deleteAd_shouldDeleteExistingAd() {
        long adId = 1L;

        when(adRepository.existsById(adId)).thenReturn(true);

        adService.deleteAd(adId);

        verify(adRepository, times(1)).existsById(adId);
        verify(adRepository, times(1)).deleteById(adId);
    }

    @Test
    void deleteAd_shouldThrowExceptionForNonExistingAd() {
        long adId = 1L;

        when(adRepository.existsById(adId)).thenReturn(false);

        Assertions.assertThrows(AdNotFoundException.class, () -> {
            adService.deleteAd(adId);
        });

        verify(adRepository, times(1)).existsById(adId);
        verify(adRepository, never()).deleteById(adId);
    }
}
