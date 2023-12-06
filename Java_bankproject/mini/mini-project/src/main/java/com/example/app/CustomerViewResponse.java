package com.example.app;
import java.math.BigDecimal;

public class CustomerViewResponse 
{
    private Long id;
    private String accountNumber;
    private BigDecimal balance;

    public CustomerViewResponse() 
    {
    }

    public CustomerViewResponse(Long id, String accountNumber, BigDecimal balance) 
    {
        this.id = id;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }
    
    public Long getId() 
    {
        return id;
    }

    public void setId(Long id) 
    {
        this.id = id;
    }

    public String getAccountNumber() 
    {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) 
    {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() 
    {
        return balance;
    }

    public void setBalance(BigDecimal balance) 
    {
        this.balance = balance;
    }
}
