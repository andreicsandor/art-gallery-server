package org.server.controller;

import org.server.dto.FilterDTO;
import org.server.service.ExhibitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VisitorController {

    @Autowired
    protected ExhibitService exhibitService;

    @GetMapping("/api/get-exhibits")
    public ResponseEntity<?> getExhibits() {
        List<Map<String, Object>> exhibits = exhibitService.getExhibits();

        if (!exhibits.isEmpty()) {
            return ResponseEntity.ok(exhibits);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("message", "No exhibits found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/api/filter-exhibits")
    public ResponseEntity<?> filterExhibits(@RequestBody FilterDTO filterDTO) {
        List<Map<String, Object>> exhibits = null;

        if (filterDTO.getFilterType().equals("Name")) {
            // Returns the exhibits that contain the name phrase
            String name = filterDTO.getFilterKeyword();
            exhibits = exhibitService.filterByName(name);
        } else if (filterDTO.getFilterType().equals("Artist")) {
            // Returns the exhibits that match the artist
            String artist = filterDTO.getFilterKeyword();
            exhibits = exhibitService.filterByArtist(artist);
        } else if (filterDTO.getFilterType().equals("Type")) {
            // Returns the exhibits that match the type
            String type = filterDTO.getFilterKeyword();
            exhibits = exhibitService.filterByType(type);
        }

        if (exhibits != null && !exhibits.isEmpty()) {
            return ResponseEntity.ok(exhibits);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("message", "No exhibits found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

}
