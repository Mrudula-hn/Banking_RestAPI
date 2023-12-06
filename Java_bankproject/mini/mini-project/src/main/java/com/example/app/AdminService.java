package com.example.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public boolean verifyCredentials(String username, String password) {
        System.out.println("Verifying credentials for name: " + username + ", password: " + password);
        Admin admin = adminRepository.findByUsernameAndPassword(username, password);
        System.out.println("Admin retrieved from the database: " + admin);

        if (admin != null) {
            admin.setAdminActive(true);
            adminRepository.save(admin);
            return true;
        }

        return false;
    }

    public boolean isAdminActive(String username) {
        Admin admin = adminRepository.findByUsername(username);
        return admin != null && admin.getAdminActive();
    }
    
    public boolean isNotAdminActive(String username) {
        Admin admin = adminRepository.findByUsername(username);
        admin.setAdminActive(false);
        return admin != null && admin.getAdminActive();
    }
    
    public Admin getAdminCredentials() {
    	return adminRepository.findById(1L).orElse(null);
    }
}
