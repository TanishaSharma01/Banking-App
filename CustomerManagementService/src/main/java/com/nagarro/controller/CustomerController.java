package com.nagarro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.entities.Customer;
import com.nagarro.exceptions.ResourceNotFoundException;
import com.nagarro.payload.ApiResponse;
import com.nagarro.services.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	//create a Customer
	@PostMapping
	public ResponseEntity<Customer> createCustomer(@RequestBody @Validated Customer customer){
		Customer Customer1 = customerService.saveCustomer(customer);
		return ResponseEntity.status(HttpStatus.CREATED).body(Customer1);
		
	}
		
	//get a single Customer
	@GetMapping("/{customerId}")
	public ResponseEntity<Customer> getSingleCustomer(@PathVariable Long customerId){
			Customer customer1 = customerService.getCustomer(customerId);
			return ResponseEntity.ok(customer1);

	}
			
	//get all Customers
	@GetMapping
	public ResponseEntity<?> getAllCustomer(){
		List<Customer> allCustomer = customerService.getAllCustomer();
		return ResponseEntity.ok(allCustomer);
	}
	
	// Update a customer
	@PutMapping("/{customerId}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable @Validated Long customerId, @RequestBody @Validated Customer newCustomerData) {
		Customer updatedCustomer = customerService.updateCustomer(customerId, newCustomerData);
        return ResponseEntity.ok(updatedCustomer);
	}

	// Endpoint to delete a customer along with the associated account
    @DeleteMapping("/{customerId}")
    public ResponseEntity<String> deleteCustomerWithAccount(@PathVariable Long customerId) {
    	boolean customerDeleted = customerService.deleteCustomerWithAccount(customerId);

        if (customerDeleted) {
        	return ResponseEntity.ok("Customer and associated accounts deleted for customerId: "+customerId);
        } else {
        	throw new RuntimeException("Failed to delete accounts for customer: " + customerId);
        }
    }
}
