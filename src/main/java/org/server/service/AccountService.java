package org.server.service;

import org.server.dto.AccountDTO;
import org.server.model.Account;
import org.server.model.Gallery;
import org.server.model.repository.AccountRepository;
import org.server.model.repository.GalleryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private GalleryRepository galleryRepository;

    public Map<String, Object> pairGallery(Account account, String gallery) {
        Map<String, Object> pair = new HashMap<>();
        pair.put("profile", account);
        pair.put("gallery", gallery);
        return pair;
    }

    public List<Map<String, Object>> getAccounts() {
        List<Account> accounts = this.accountRepository.findAll();
        List<Map<String, Object>> detailedAccounts = new ArrayList<>();

        for (Account account : accounts) {
            Gallery gallery = galleryRepository.findByEmployee(account);
            String galleryName;
            if (gallery != null) {
                galleryName = gallery.getName();
            } else {
                galleryName = "—";
            }

            Map<String, Object> detailedAccount = pairGallery(account, galleryName);
            detailedAccounts.add(detailedAccount);
        }

        return detailedAccounts;
    }

    public List<Map<String, Object>> filterByRole(String role) {
        List<Account> accounts = this.accountRepository.findByRole(role);
        List<Map<String, Object>> detailedAccounts = new ArrayList<>();

        for (Account account : accounts) {
            Gallery gallery = galleryRepository.findByEmployee(account);
            String galleryName;
            if (gallery != null) {
                galleryName = gallery.getName();
            } else {
                galleryName = "—";
            }

            Map<String, Object> detailedAccount = pairGallery(account, galleryName);
            detailedAccounts.add(detailedAccount);
        }

        return detailedAccounts;
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
            Account updatedAccount = new Account();
            updatedAccount.setId(oldAccount.getId());
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

                // Save the updated account
                this.accountRepository.save(updatedAccount);

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
