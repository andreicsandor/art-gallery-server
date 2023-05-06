package org.server.controller;

import org.server.model.Account;
import org.server.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/get-credentials")
    @ResponseBody
    public Account getUserCredentials(@RequestParam String name, @RequestParam String password) {
        return this.accountService.getCredentials(name, password);
    }
}
