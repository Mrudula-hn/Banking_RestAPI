package com.example.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private CustomerService customerService;
    
    @GetMapping("/home")
    public String home( ) {
    	return "Welcome to bank";
    }

    @PostMapping("/login")
    public String login(@RequestBody Admin admin) {
        if (adminService.verifyCredentials(admin.getUsername(), admin.getPassword())) {
            return "Logged in successfully";
        } else {
            return "Enter correct credentials";
        }
    }
    
//    @PostMapping("/customer/login")
//    public ResponseEntity<String> customerLogin(@RequestParam String accountNumber,
//                                               @RequestParam String temporaryPassword,
//                                               @RequestParam(required = false) String newPassword) {
//        boolean loginResult = customerService.loginCustomer(accountNumber, temporaryPassword, newPassword);
//
//        if (loginResult) {
//            return ResponseEntity.ok("Login successful");
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid login credentials");
//        }
//    }
    
    @PostMapping("/customer/login")
	public ResponseEntity<?> loginCustomer(@RequestBody CustomerLoginRequest loginRequest) {
	    String accountNumber = loginRequest.getAccountNumber();
	    String temporaryPassword = loginRequest.getTemporaryPassword();
	    String permanentPassword = loginRequest.getPermanentPassword();

	    if (customerService.loginCustomer(accountNumber, temporaryPassword, permanentPassword)) {
	        return new ResponseEntity<>("Login successful", HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>("Login failed. Customer not found or unauthorized.", HttpStatus.UNAUTHORIZED);
	    }
	}
}
