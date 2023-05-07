package org.server.service;

import org.server.dto.AccountDTO;
import org.server.model.Account;
import org.server.model.Exhibit;
import org.server.model.Gallery;
import org.server.model.repository.AccountRepository;
import org.server.model.repository.GalleryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private GalleryRepository galleryRepository;

    public List<Account> getAccounts() {
        return this.accountRepository.findAll();
    }

    public List<Account> filterByRole(String role) {
        List<Account> accounts = this.accountRepository.findByRole(role);
        Collections.sort(accounts, Comparator.comparing(Account::getLastName));
        return accounts;
    }

    public Boolean createAccount(AccountDTO accountDTO) {
        Account account = new Account(
            accountDTO.getFirstName(),
            accountDTO.getLastName(),
            accountDTO.getRole(),
            accountDTO.getUsername(),
            accountDTO.getPassword()
        );

        try {
            if (account.getRole().equals("Employee")) {
                String galleryName = accountDTO.getGallery();
                Gallery gallery = galleryRepository.findByName(galleryName);
                gallery.addEmployee(account);
            }
            this.accountRepository.save(account);
            return Boolean.TRUE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    public Boolean updateAccount(Long accountId, AccountDTO accountDTO) {
        Account oldAccount = this.accountRepository.findById(accountId).orElse(null);

        if (oldAccount != null) {
            Account updatedAccount = oldAccount;
            updatedAccount.setFirstName(accountDTO.getFirstName());
            updatedAccount.setLastName(accountDTO.getLastName());
            updatedAccount.setRole(accountDTO.getRole());
            updatedAccount.setUsername(accountDTO.getUsername());
            updatedAccount.setPassword(accountDTO.getPassword());

            try {
                if (oldAccount.getRole().equals("Employee")) {
                    // Find the associated gallery
                    Gallery oldGallery = galleryRepository.findByEmployee(oldAccount);

                    // Remove the old account from the gallery
                    oldGallery.removeEmployee(oldAccount);

                    // Save the updated gallery
                    this.galleryRepository.save(oldGallery);
                }

                if (updatedAccount.getRole().equals("Employee")) {
                    // Find the associated gallery
                    Gallery newGallery = galleryRepository.findByName(accountDTO.getGallery());

                    // Save the updated account
                    this.accountRepository.save(updatedAccount);

                    // Add the updated account to the gallery
                    newGallery.addEmployee(updatedAccount);

                    // Save the updated gallery
                    this.galleryRepository.save(newGallery);

                    // Return value and exit function
                    return Boolean.TRUE;
                }

                // Save the updated account
                this.accountRepository.save(updatedAccount);
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

    public Account authenticate(String name, String password) {
        Account account = accountRepository.findByUsername(name);
        if (account != null && account.getPassword().equals(password)) {
            return account;
        } else {
            return null;
        }
    }
}
