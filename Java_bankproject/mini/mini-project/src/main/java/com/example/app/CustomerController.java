package com.example.app;

//import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	@Autowired
	private AdminService adminService;

	@PostMapping("/createcustomer")
	public String createCustomer(@RequestBody Customer customer) {
		Admin admin = adminService.getAdminCredentials();

		if (admin != null) {
			boolean isAdminActive = adminService.verifyCredentials(admin.getUsername(), admin.getPassword());

			if (isAdminActive) {
				boolean customerCreated = customerService.createCustomer(customer, admin.getUsername(),
						admin.getPassword());

				if (customerCreated) {
					return "Customer created successfully";
				} else {
					return "Failed to create customer. Please try again.";
				}
			} else {
				return "Not allowed to create customer. Admin may not be active.";
			}
		} else {
			return "Admin credentials not found. Unable to create customer.";
		}
	}
	


	@GetMapping("/customer/{accountNo}/viewAccount")
	public ResponseEntity<?> viewCustomerAccount(@PathVariable String accountNo) {
		Customer customer = customerService.getCustomerByAccountNumber(accountNo);

		if (customer != null && !adminService.isAdminActive(customer.getAccountNumber())) {
			// Create a simplified response with limited details
			CustomerViewResponse viewResponse = new CustomerViewResponse(customer.getId(), customer.getAccountNumber(),
					customer.getInitialBalance());

			return new ResponseEntity<>(viewResponse, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Customer not found or unauthorized", HttpStatus.NOT_FOUND);
		}

	}

}
