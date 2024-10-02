package com.example.godgame.Image.country.service;


import com.example.godgame.Image.country.entity.CountryImage;
import com.example.godgame.Image.country.repository.CountryImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CountryImageService {

    private final CountryImageRepository countryImageRepository;

    public CountryImageService(CountryImageRepository countryImageRepository) {
        this.countryImageRepository = countryImageRepository;
    }

    public List<CountryImage> findRandomImage(int count){
        return countryImageRepository.findRandomCountryImages(count);
    }
}
