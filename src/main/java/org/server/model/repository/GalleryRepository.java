package org.server.model.repository;

import org.server.model.Account;
import org.server.model.Exhibit;
import org.server.model.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GalleryRepository extends JpaRepository<Gallery, Long> {

    Gallery findByName(String name);

    default Gallery findByExhibit(Exhibit exhibit) {
        List<Gallery> galleries = findAll();
        for (Gallery gallery : galleries) {
            if (gallery.getExhibits() != null && gallery.getExhibits().contains(exhibit)) {
                return gallery;
            }
        }
        return null;
    }

    default Gallery findByEmployee(Account employee) {
        List<Gallery> galleries = findAll();
        for (Gallery gallery : galleries) {
            if (gallery.getEmployees() != null && gallery.getEmployees().contains(employee)) {
                return gallery;
            }
        }
        return null;
    }
}
