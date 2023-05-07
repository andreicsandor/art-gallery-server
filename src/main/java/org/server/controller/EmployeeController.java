package org.server.controller;

import org.server.dto.ExhibitDTO;
import org.server.model.Exhibit;
import org.server.service.ExhibitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class EmployeeController {

    @Autowired
    private ExhibitService exhibitService;

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

    @PostMapping("/create-exhibit")
    public ResponseEntity<?> createExhibit(@RequestBody ExhibitDTO exhibitDTO) {
        // Convert DTO to Exhibit entity and call the service layer
        Boolean success = exhibitService.createExhibit(exhibitDTO);
        Map<String, String> response = new HashMap<>();

        // Return appropriate ResponseEntity based on success
        if (success) {
            response.put("message", "Exhibit successfully created.");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            response.put("message", "Failed to create exhibit.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/update-exhibit/{exhibitID}")
    public ResponseEntity<?> updateExhibit(@PathVariable Long exhibitID, @RequestBody ExhibitDTO exhibitDTO) {
        // Convert DTO to Exhibit entity and call the service layer
        Boolean success = exhibitService.updateExhibit(exhibitID, exhibitDTO);
        Map<String, String> response = new HashMap<>();

        // Return appropriate ResponseEntity based on success
        if (success) {
            response.put("message", "Exhibit successfully updated.");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            response.put("message", "Failed to update exhibit.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/delete-exhibit/{exhibitID}")
    public ResponseEntity<?> deleteExhibit(@PathVariable Long exhibitID) {
        // Convert DTO to Exhibit entity and call the service layer
        Boolean success = exhibitService.deleteExhibit(exhibitID);
        Map<String, String> response = new HashMap<>();

        // Return appropriate ResponseEntity based on success
        if (success) {
            response.put("message", "Exhibit successfully deleted.");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            response.put("message", "Failed to delete exhibit.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
