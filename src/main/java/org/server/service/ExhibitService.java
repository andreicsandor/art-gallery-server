package org.server.service;

import org.server.dto.ExhibitDTO;
import org.server.model.Exhibit;
import org.server.model.Gallery;
import org.server.model.repository.ExhibitRepository;
import org.server.model.repository.GalleryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class ExhibitService {

    @Autowired
    private ExhibitRepository exhibitRepository;

    @Autowired
    private GalleryRepository galleryRepository;

    public List<Exhibit> getExhibits() {
        List<Exhibit> exhibits = this.exhibitRepository.findAll();
        Collections.sort(exhibits, Comparator.comparingInt(Exhibit::getYear));
        return exhibits;
    }

    public Boolean createExhibit(ExhibitDTO exhibitDTO) {
        Exhibit exhibit = new Exhibit(
            exhibitDTO.getName(),
            exhibitDTO.getArtist(),
            exhibitDTO.getType(),
            exhibitDTO.getYear()
        );

        try {
            // Save the created exhibit
            this.exhibitRepository.save(exhibit);

            // Find the associated gallery
            Gallery gallery = galleryRepository.findByName(exhibitDTO.getGallery());

            // Add the created exhibit to the gallery
            gallery.addExhibit(exhibit);

            // Save the updated gallery
            this.galleryRepository.save(gallery);

            return Boolean.TRUE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    public Boolean updateExhibit(Long exhibitId, ExhibitDTO exhibitDTO) {
        Exhibit oldExhibit = this.exhibitRepository.findById(exhibitId).orElse(null);

        if (oldExhibit != null) {
            Exhibit updatedExhibit = oldExhibit;
            updatedExhibit.setName(exhibitDTO.getName());
            updatedExhibit.setArtist(exhibitDTO.getArtist());
            updatedExhibit.setType(exhibitDTO.getType());
            updatedExhibit.setYear(exhibitDTO.getYear());

            try {
                // Find the old associated gallery
                Gallery oldGallery = galleryRepository.findByExhibit(oldExhibit);

                // Remove the old exhibit from the gallery
                oldGallery.removeExhibit(oldExhibit);

                // Save the updated gallery
                this.galleryRepository.save(oldGallery);

                // Save the updated exhibit
                this.exhibitRepository.save(updatedExhibit);

                // Find the new associated gallery
                Gallery newGallery = galleryRepository.findByName(exhibitDTO.getGallery());

                // Add the created exhibit to the gallery
                newGallery.addExhibit(updatedExhibit);

                // Save the updated gallery
                this.galleryRepository.save(newGallery);

                return Boolean.TRUE;
            } catch (Exception e) {
                return Boolean.FALSE;
            }
        } else {
            return Boolean.FALSE;
        }
    }

    public Boolean deleteExhibit(Long exhibitId) {
        try {
            this.exhibitRepository.deleteById(exhibitId);
            return Boolean.TRUE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }
}
