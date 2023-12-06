package com.example.app;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity

public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_number", referencedColumnName = "account_number")
    private Customer customer;


    @Temporal(TemporalType.DATE)
    private Date transactionDate;

    @Temporal(TemporalType.TIME)
    private Date transactionTime;

    private String creditDebit;

    private BigDecimal amount;

    private BigDecimal finalBalance;

   

    public Transaction() {
    }

    public Transaction(Customer customer, Date transactionDate, Date transactionTime, String creditDebit,
                       BigDecimal amount, BigDecimal finalBalance) {
        this.customer = customer;
        this.transactionDate = transactionDate;
        this.transactionTime = transactionTime;
        this.creditDebit = creditDebit;
        this.amount = amount;
        this.finalBalance = finalBalance;
    }

    // Getters...

    public Long getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public String getCreditDebit() {
        return creditDebit;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getFinalBalance() {
        return finalBalance;
    }

    // Setters...

    public void setId(Long id) {
        this.id = id;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    public void setCreditDebit(String creditDebit) {
        this.creditDebit = creditDebit;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setFinalBalance(BigDecimal finalBalance) {
        this.finalBalance = finalBalance;
    }
}
