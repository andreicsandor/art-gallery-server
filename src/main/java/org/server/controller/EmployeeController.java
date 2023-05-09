package org.server.controller;

import org.server.dto.ExhibitDTO;
import org.server.dto.ItemDTO;
import org.server.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EmployeeController extends VisitorController {

    @Autowired
    protected ItemService itemService;

    @PostMapping("/api/create-exhibit")
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

    @PutMapping("/api/update-exhibit/{exhibitID}")
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

    @DeleteMapping("/api/delete-exhibit/{exhibitID}")
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

    @PostMapping("/api/sell-item/")
    public ResponseEntity<?> sellItem(@PathVariable Long exhibitID, @RequestBody ItemDTO itemDTO) {
        // Convert Item DTO to Exhibit entity and call the service layer
        Boolean sellSuccess = itemService.createItem(itemDTO);

        // Convert Exhibit DTO to Exhibit entity and call the service layer
        Boolean deleteSuccess = exhibitService.deleteExhibit(exhibitID);

        Map<String, String> response = new HashMap<>();

        // Return appropriate ResponseEntity based on success
        if (sellSuccess && deleteSuccess) {
            response.put("message", "Item successfully sold.");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            response.put("message", "Failed to sell item.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
