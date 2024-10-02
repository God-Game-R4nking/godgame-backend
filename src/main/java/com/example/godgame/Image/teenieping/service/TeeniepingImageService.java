package com.example.godgame.Image.teenieping.service;


import com.example.godgame.Image.teenieping.entity.TeeniepingImage;
import com.example.godgame.Image.teenieping.repository.TeeniepingImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TeeniepingImageService {

    private final TeeniepingImageRepository teeniepingImageRepository;

    public TeeniepingImageService(TeeniepingImageRepository teeniepingImageRepository) {
        this.teeniepingImageRepository = teeniepingImageRepository;
    }

    public List<TeeniepingImage> findRandomImage(int count){
        return teeniepingImageRepository.findRandomTeeinepingImages(count);
    }
}
