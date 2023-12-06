package com.example.app;

public class CustomerLoginRequest {
    private String accountNumber;
    private String temporaryPassword;
    private String permanentPassword;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getTemporaryPassword() {
        return temporaryPassword;
    }

    public void setTemporaryPassword(String temporaryPassword) {
        this.temporaryPassword = temporaryPassword;
    }

    public String getPermanentPassword() {
        return permanentPassword;
    }

    public void setPermanentPassword(String permanentPassword) {
        this.permanentPassword = permanentPassword;
    }
}

