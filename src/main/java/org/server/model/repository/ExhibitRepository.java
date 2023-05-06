package org.server.model.repository;

import org.server.model.Exhibit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExhibitRepository extends JpaRepository<Exhibit, Long> {

    @Query("SELECT e FROM Exhibit e WHERE e.name = :name AND e.artist = :artist")
    Exhibit findByNameArtist(@Param("name") String keywordName, @Param("artist") String keywordArtist);

    List<Exhibit> findByArtist(String artist);

    List<Exhibit> findByType(String type);
}
