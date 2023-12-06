package com.example.app;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Admin {

    @Id
    private Long id;
    private String name;
    private String empRefNo;
    private String username; 
    private String password; 
    private Boolean adminactive;

    public Admin() {
    }

    public Admin(String name, String empRefNo) {
        this.name = name;
        this.empRefNo = empRefNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRefNo() {
        return empRefNo;
    }

    public void setRefNo(String empRefNo) {
        this.empRefNo = empRefNo;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public Boolean getAdminActive() {
        return adminactive;
    }

    public void setAdminActive(Boolean adminactive) {
        this.adminactive = adminactive;
    }
}
