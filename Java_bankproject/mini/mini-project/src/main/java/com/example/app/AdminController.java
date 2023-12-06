package com.example.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/customers/{customerId}")
    public Customer getCustomerById(@PathVariable Long customerId) {
        return customerService.getCustomerById(customerId);
    }

    @PutMapping("/customers/{customerId}")
    public String updateCustomer(@PathVariable Long customerId, @RequestBody Customer updatedCustomer) {
        if (customerService.updateCustomer(customerId, updatedCustomer)) {
            return "Customer updated successfully";
        } else {
            return "Failed to update customer. Customer not found.";
        }
    }

    @DeleteMapping("/customers/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long customerId) {
        try {
            if (customerService.deleteCustomer(customerId)) {
                return ResponseEntity.ok("Customer deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to delete customer. Customer not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request.");
        }
}
}
