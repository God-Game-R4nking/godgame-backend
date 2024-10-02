package com.example.godgame.Image.people.service;


import com.example.godgame.Image.people.entity.PeopleImage;
import com.example.godgame.Image.people.repository.PeopleImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PeopleImageService {
    private final PeopleImageRepository peopleImageRepository;

    public PeopleImageService(PeopleImageRepository peopleImageRepository) {
        this.peopleImageRepository = peopleImageRepository;
    }

    public List<PeopleImage> findRandomImage(int count){
        return peopleImageRepository.findRandomPeopleImages(count);
    }

}
