package com.example.app;

import java.math.BigDecimal;

//import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer")

public class TransactionController {
	
	 @Autowired
	    private CustomerService customerService;
	 
	 @PostMapping("/{accountNumber}/deposite")
	    public ResponseEntity<?> deposit(
	            @PathVariable String accountNumber,
	            @RequestParam BigDecimal amount,
	            @RequestBody CustomerLoginRequest loginRequest) {
		 
		    
	        if (customerService.loginCustomer(accountNumber, loginRequest.getTemporaryPassword(), loginRequest.getPermanentPassword())) {
	            if (customerService.deposit(accountNumber, amount)) {
	                return new ResponseEntity<>("Deposit successful", HttpStatus.OK);
	            } else {
	                return new ResponseEntity<>("Failed to deposit. Customer not found or unauthorized.", HttpStatus.NOT_FOUND);
	            }
	        } else {
	            return new ResponseEntity<>("Login failed. Customer not found or unauthorized.", HttpStatus.UNAUTHORIZED);
	        }
	    }
	 
	 @PostMapping("/{accountNumber}/withdraw")
	 public ResponseEntity<?> withdraw(
	         @PathVariable String accountNumber,
	         @RequestParam BigDecimal amount,
	         @RequestBody CustomerLoginRequest loginRequest) {
	     if (customerService.loginCustomer(accountNumber, loginRequest.getTemporaryPassword(), loginRequest.getPermanentPassword())) {
	         if (customerService.withdraw(accountNumber, amount)) {
	             return new ResponseEntity<>("Withdrawal successful", HttpStatus.OK);
	         } else {
	             return new ResponseEntity<>("Failed to withdraw. Customer not found, unauthorized, or insufficient balance.", HttpStatus.NOT_FOUND);
	         }
	     } else {
	         return new ResponseEntity<>("Login failed. Customer not found or unauthorized.", HttpStatus.UNAUTHORIZED);
	     }
	 }
	 
}
