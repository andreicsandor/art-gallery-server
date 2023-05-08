package org.server.service;

import org.server.dto.ExhibitDTO;
import org.server.model.Exhibit;
import org.server.model.Gallery;
import org.server.model.repository.ExhibitRepository;
import org.server.model.repository.GalleryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExhibitService {

    @Autowired
    private ExhibitRepository exhibitRepository;

    @Autowired
    private GalleryRepository galleryRepository;

    public Map<String, Object> pairGallery(Exhibit exhibit, String gallery) {
        Map<String, Object> pair = new HashMap<>();
        pair.put("item", exhibit);
        pair.put("gallery", gallery);
        return pair;
    }

    public List<Map<String, Object>> getExhibits() {
        List<Exhibit> exhibits = this.exhibitRepository.findAll();
        List<Map<String, Object>> detailedExhibits = new ArrayList<>();

        for (Exhibit exhibit : exhibits) {
            String gallery = galleryRepository.findByExhibit(exhibit).getName();

            Map<String, Object> detailedExhibit = pairGallery(exhibit, gallery);
            detailedExhibits.add(detailedExhibit);
        }

        return detailedExhibits;
    }

    public List<Map<String, Object>> filterByName(String name) {
        List<Exhibit> exhibits = this.exhibitRepository.findByName(name);
        List<Map<String, Object>> detailedExhibits = new ArrayList<>();

        for (Exhibit exhibit : exhibits) {
            String gallery = galleryRepository.findByExhibit(exhibit).getName();

            Map<String, Object> detailedExhibit = pairGallery(exhibit, gallery);
            detailedExhibits.add(detailedExhibit);
        }

        return detailedExhibits;
    }

    public List<Map<String, Object>> filterByArtist(String artist) {
        List<Exhibit> exhibits = this.exhibitRepository.findByArtist(artist);
        List<Map<String, Object>> detailedExhibits = new ArrayList<>();

        for (Exhibit exhibit : exhibits) {
            String gallery = galleryRepository.findByExhibit(exhibit).getName();

            Map<String, Object> detailedExhibit = pairGallery(exhibit, gallery);
            detailedExhibits.add(detailedExhibit);
        }

        return detailedExhibits;
    }

    public List<Map<String, Object>> filterByType(String type) {
        List<Exhibit> exhibits = this.exhibitRepository.findByType(type);
        List<Map<String, Object>> detailedExhibits = new ArrayList<>();

        for (Exhibit exhibit : exhibits) {
            String gallery = galleryRepository.findByExhibit(exhibit).getName();

            Map<String, Object> detailedExhibit = pairGallery(exhibit, gallery);
            detailedExhibits.add(detailedExhibit);
        }

        return detailedExhibits;
    }

    public List<String> getArtistNames() {
        List<Exhibit> exhibits = this.exhibitRepository.findAll();

        Set<String> artistsSet = new HashSet<>();
        for (Exhibit exhibit : exhibits) {
            artistsSet.add(exhibit.getArtist());
        }

        List<String> artists = new ArrayList<>(artistsSet);

        return artists;
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
