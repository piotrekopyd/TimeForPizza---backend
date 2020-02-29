package com.backend.timeforpizza.timeforpizzabackend.service;

import com.backend.timeforpizza.timeforpizzabackend.repository.ImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ImagesService {

    private ImagesRepository imagesRepository;

    ImagesService(@Qualifier("imagesRepository") ImagesRepository imagesRepository) {
        this.imagesRepository = imagesRepository;
    }


}