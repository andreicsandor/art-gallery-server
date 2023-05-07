package org.server.controller;

import org.server.model.Exhibit;
import org.server.service.ExhibitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VisitorController {

    @Autowired
    protected ExhibitService exhibitService;

    @GetMapping("/exhibits")
    public ResponseEntity<?> getExhibits() {
        List<Exhibit> exhibits = exhibitService.getExhibits();
        Map<String, String> response = new HashMap<>();

        if (!exhibits.isEmpty()) {
            return ResponseEntity.ok(exhibits);
        } else {
            response.put("message", "No exhibits found.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }
    }
}
