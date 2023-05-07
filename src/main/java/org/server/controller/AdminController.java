package org.server.controller;

import org.server.dto.AccountDTO;
import org.server.model.Account;
import org.server.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/accounts")
    public ResponseEntity<?> getAccounts() {
        List<Account> accounts = accountService.getAccounts();
        if (!accounts.isEmpty()) {
            return ResponseEntity.ok(accounts);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No accounts found.");
        }
    }

    @PreAuthorize("hasRole('admin') and isAuthenticated()")
    @PostMapping("/create-account")
    public ResponseEntity<?> createAccount(@RequestBody AccountDTO accountDTO) {
        // Convert DTO to Account entity and call the service layer
        Boolean success = accountService.createAccount(accountDTO);

        // Return appropriate ResponseEntity based on success
        if (success) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Account successfully created.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create account.");
        }
    }

    @PreAuthorize("hasRole('admin') and isAuthenticated()")
    @PutMapping("/update-account/{accountID}")
    public ResponseEntity<?> updateAccount(@PathVariable Long accountID, @RequestBody AccountDTO accountDTO) {
        // Convert DTO to Account entity and call the service layer
        Boolean success = accountService.updateAccount(accountID, accountDTO);

        // Return appropriate ResponseEntity based on success
        if (success) {
            return ResponseEntity.status(HttpStatus.OK).body("Account successfully updated.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update account.");
        }
    }

    @PreAuthorize("hasRole('admin') and isAuthenticated()")
    @DeleteMapping("/delete-account/{accountID}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long accountID) {
        // Convert DTO to Account entity and call the service layer
        Boolean success = accountService.deleteAccount(accountID);

        // Return appropriate ResponseEntity based on success
        if (success) {
            return ResponseEntity.status(HttpStatus.OK).body("Account successfully deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete account.");
        }
    }
}
