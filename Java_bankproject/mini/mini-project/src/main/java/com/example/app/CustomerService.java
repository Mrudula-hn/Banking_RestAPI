package com.example.app;

import org.apache.commons.lang3.RandomStringUtils;
//import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
//import java.util.Optional;

import javax.transaction.Transactional;

@Service
public class CustomerService 
{
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AdminService adminService;
    @Autowired
    private TransactionRepository transactionRepository;

    public boolean createCustomer(Customer customer, String adminUsername, String adminPassword) 
    {
        if (adminService.verifyCredentials(adminUsername, adminPassword) && adminService.isAdminActive(adminUsername)) 
        {
            System.out.println("Admin credentials verified and active.");
            System.out.println("Customer details before saving: "+ customer.getFullName());
            
            customer.setAccountNumber(RandomStringUtils.randomAlphanumeric(10));
            customer.setTemporaryPassword(RandomStringUtils.randomAlphanumeric(8));
            customer.setInitialBalance(BigDecimal.valueOf(1000.00));
            customer.setPermanentPassword(RandomStringUtils.randomAlphanumeric(8));

            //boolean admin = adminService.isNotAdminActive(adminUsername);
          
            customerRepository.save(customer);
            
            System.out.println("Customer account no: " + customer.getAccountNumber());
            System.out.println("Customer temp password: " + customer.getTemporaryPassword());
            return true;
        } 
        else 
        {
            return false;
        }
    }

  

    public List<Customer> getAllCustomers() 
    {
        List<Customer> customers=customerRepository.findAll();

        if (adminService.isAdminActive(adminService.getAdminCredentials().getUsername())) 
        {
            for (Customer customer : customers) 
            {
                customer.setTemporaryPassword(null);
                customer.setInitialBalance(null);
            }
        }

        return customers;
    }

    public Customer getCustomerById(Long customerId) 
    {
        return customerRepository.findById(customerId).orElse(null);
    }

    public boolean updateCustomer(Long customerId, Customer updatedCustomer) 
    {
        if (customerRepository.existsById(customerId)) 
        {
            updatedCustomer.setId(customerId);
            customerRepository.save(updatedCustomer);
            return true;
        }
        return false;
    }

    public boolean deleteCustomer(Long customerId) {
        try {
            if (customerRepository.existsById(customerId)) {
                customerRepository.deleteById(customerId);
                return true;
            }
            return false;
        } catch (Exception e) {
            // Log or handle the exception as needed
            return false;
        }
    }


public Customer getCustomerByAccountNumber(String accountNumber) {
    return customerRepository.findByAccountNumber(accountNumber);
}

@Transactional
public boolean loginCustomer(String accountNumber, String temporaryPassword, String newPassword) {
    Customer customer = customerRepository.findByAccountNumber(accountNumber);

    if (customer != null) {
        if (customer.getPermanentPassword() != null) {
            if (!customer.getPermanentPassword().equals(newPassword)) {
                return false;
            }
        } else {
            if (temporaryPassword != null && !customer.getTemporaryPassword().equals(temporaryPassword)) {
                return false;
            }
        }

        if (newPassword != null) {
            updateCustomerPassword(customer, newPassword);
        }
        customerRepository.save(customer);
        return true;
    }
    return false;

}

private void updateCustomerPassword(Customer customer, String newPassword) {
    customer.setPermanentPassword(newPassword);
    customer.setTemporaryPassword(null); // To clear temporary password after setting new permanent password
    customerRepository.save(customer);
}


public CustomerViewResponse viewCustomer(String accountNumber) {
    Customer customer = customerRepository.findByAccountNumber(accountNumber);

    if (customer != null) {
        BigDecimal balance = getFinalBalance(accountNumber);
        return new CustomerViewResponse(customer.getId(), accountNumber, balance);
    }
    return null;
}

public boolean deposit(String accountNumber, BigDecimal amount) {
    Customer customer = customerRepository.findByAccountNumber(accountNumber);

    if (customer != null) {
        BigDecimal finalBalance = getFinalBalance(accountNumber).add(amount);
        
        // Log relevant information
        System.out.println("Depositing " + amount + " to account " + accountNumber);
        System.out.println("Previous balance: " + getFinalBalance(accountNumber));
        System.out.println("New balance after deposit: " + finalBalance);

        saveTransaction(customer, "cr", amount, finalBalance);
        return true;
    }

    return false;
}

@Transactional
public boolean withdraw(String accountNumber, BigDecimal amount) {
    Customer customer = customerRepository.findByAccountNumber(accountNumber);
    BigDecimal finalBalance = getFinalBalance(accountNumber).subtract(amount);

    if (customer != null) {
        // Log relevant information
    	 System.out.println("Customer found: " + customer);
        System.out.println("Withdrawing " + amount + " from account " + accountNumber);
        System.out.println("Previous balance: " + getFinalBalance(accountNumber));
        System.out.println("New balance after withdrawal: " + finalBalance);

        // Update customer balance
        customer.setInitialBalance(finalBalance);
        customerRepository.save(customer);
        customerRepository.save(customer);

        // Save transaction
        saveTransaction(customer, "dr", amount, finalBalance);
        return true;
    } else {
        System.out.println("Customer with account number " + accountNumber + " does not exist.");
        return false;
    }
}



    private BigDecimal getFinalBalance(String accountNumber) 
    {
        List<Transaction> transactions = transactionRepository.findByCustomer_AccountNumber(accountNumber);
        if (!transactions.isEmpty()) 
        {
            return transactions.get(transactions.size() - 1).getFinalBalance();
        } 
        else 
        {
            return customerRepository.findByAccountNumber(accountNumber).getInitialBalance();
        }
    }

    private void saveTransaction(Customer customer, String creditDebit, BigDecimal amount, BigDecimal finalBalance) {
    	if (customer != null) {
            Date currentDate = new Date();
            Transaction transaction = new Transaction(customer, currentDate, currentDate, creditDebit, amount, finalBalance);
            transactionRepository.save(transaction);
        } else {
            System.out.println("Cannot save transaction. Customer is null.");
        }
    }
}
    
  

