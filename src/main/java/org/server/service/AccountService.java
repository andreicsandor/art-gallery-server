package org.server.service;

import org.server.dto.AccountDTO;
import org.server.model.Account;
import org.server.model.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Boolean createAccount(AccountDTO accountDTO) {
        Account account = new Account(
            accountDTO.getFirstName(),
            accountDTO.getLastName(),
            accountDTO.getRole(),
            accountDTO.getUsername(),
            accountDTO.getPassword()
        );

        try {
            this.accountRepository.save(account);
            return Boolean.TRUE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    public Boolean updateAccount(Long accountId, AccountDTO accountDTO) {
        Optional<Account> optionalAccount = this.accountRepository.findById(accountId);

        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            account.setFirstName(accountDTO.getFirstName());
            account.setLastName(accountDTO.getLastName());
            account.setRole(accountDTO.getRole());
            account.setUsername(accountDTO.getUsername());
            account.setPassword(accountDTO.getPassword());

            try {
                this.accountRepository.save(account);
                return Boolean.TRUE;
            } catch (Exception e) {
                return Boolean.FALSE;
            }
        } else {
            return Boolean.FALSE;
        }
    }

    public Boolean deleteAccount(Long accountId) {
        try {
            this.accountRepository.deleteById(accountId);
            return Boolean.TRUE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    public Account getCredentials(String name, String password) {
        Account account = accountRepository.findByUsername(name);
        if (account != null && account.getPassword().equals(password)) {
            return account;
        } else {
            return null;
        }
    }
}
