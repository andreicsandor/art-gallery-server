package org.server.controller;

import org.server.dto.LoginDTO;
import org.server.model.Account;
import org.server.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/api/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginDTO loginDTO) {
        Map<String, String> response = new HashMap<>();
        try {
            Account account = accountService.authenticate(loginDTO.getUsername(), loginDTO.getPassword());
            if (account != null) {
                String gallery = accountService.getAccountGallery(account);
                response.put("message", "Login successful.");
                response.put("username", account.getUsername());
                response.put("role", account.getRole());
                response.put("gallery", gallery);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                response.put("message", "Invalid username or password.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } catch (AuthenticationException e) {
            response.put("message", "Login failed.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}
