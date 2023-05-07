package org.server.controller;

import org.server.dto.AccountDTO;
import org.server.dto.FilterDTO;
import org.server.model.Account;
import org.server.model.Exhibit;
import org.server.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AdminController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/get-accounts")
    public ResponseEntity<?> getAccounts() {
        List<Account> accounts = accountService.getAccounts();

        if (!accounts.isEmpty()) {
            return ResponseEntity.ok(accounts);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("message", "No accounts found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/filter-accounts")
    public ResponseEntity<?> filterAccounts(@RequestBody FilterDTO filterDTO) {
        List<Account> accounts = null;

        if (filterDTO.getFilterType().equals("Role")) {
            // Returns the exhibits that contain the name phrase
            String role = filterDTO.getFilterKeyword();
            accounts = accountService.filterByRole(role);
        }

        if (accounts != null && !accounts.isEmpty()) {
            return ResponseEntity.ok(accounts);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("message", "No accounts found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping("/create-account")
    public ResponseEntity<?> createAccount(@RequestBody AccountDTO accountDTO) {
        // Convert DTO to Account entity and call the service layer
        Boolean success = accountService.createAccount(accountDTO);
        Map<String, String> response = new HashMap<>();

        // Return appropriate ResponseEntity based on success
        if (success) {
            response.put("message", "Account successfully created.");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            response.put("message", "Failed to create account.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/update-account/{accountID}")
    public ResponseEntity<?> updateAccount(@PathVariable Long accountID, @RequestBody AccountDTO accountDTO) {
        // Convert DTO to Account entity and call the service layer
        Boolean success = accountService.updateAccount(accountID, accountDTO);
        Map<String, String> response = new HashMap<>();

        // Return appropriate ResponseEntity based on success
        if (success) {
            response.put("message", "Account successfully updated.");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            response.put("message", "Failed to update account.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/delete-account/{accountID}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long accountID) {
        // Convert DTO to Account entity and call the service layer
        Boolean success = accountService.deleteAccount(accountID);
        Map<String, String> response = new HashMap<>();

        // Return appropriate ResponseEntity based on success
        if (success) {
            response.put("message", "Account successfully deleted.");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            response.put("message", "Failed to delete account.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
