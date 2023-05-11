package org.server.model.repository;

import org.server.model.Exhibit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExhibitRepository extends JpaRepository<Exhibit, Long> {

    @Query("SELECT e FROM Exhibit e ORDER BY e.year")
    List<Exhibit> findAll();

    @Query("SELECT e FROM Exhibit e WHERE LOWER(e.name) LIKE %:name%")
    List<Exhibit> findByName(String name);

    List<Exhibit> findByArtist(String artist);

    List<Exhibit> findByType(String type);
}
