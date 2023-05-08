package org.server.service;

import org.server.model.Gallery;
import org.server.model.repository.GalleryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GalleryService {

    @Autowired
    private GalleryRepository galleryRepository;

    public List<String> getGalleryNames() {
        List<Gallery> galleries = this.galleryRepository.findAll();

        List<String> galleryNames = new ArrayList<>();
        for (Gallery gallery : galleries) {
            galleryNames.add(gallery.getName());
        }

        return galleryNames;
    }
}
