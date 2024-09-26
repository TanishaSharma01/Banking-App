package com.nagarro.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.nagarro.entities.Customer;
import com.nagarro.exceptions.InvalidDataException;
import com.nagarro.exceptions.ResourceNotFoundException;
import com.nagarro.repo.CustomerRepo;
import com.nagarro.services.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	private CustomerRepo customerRepo;
	
	private final WebClient webClient;
	private final String customerServiceBaseUrl;
	
	public CustomerServiceImpl(WebClient.Builder webClientBuilder, @Value("${account.service.base.url}") String customerServiceBaseUrl) {
        this.webClient = webClientBuilder.baseUrl(customerServiceBaseUrl).build();
        this.customerServiceBaseUrl = customerServiceBaseUrl;
    }

	//to save customer in repo
	@Override
	public Customer saveCustomer(Customer customer) {
		String email = customer.getEmail();
        customerRepo.findByEmail(email)
            .ifPresent(existingCustomer -> {
                throw new InvalidDataException("Customer with email " + email + " already exists");
            });
        // generate unique user id
        return customerRepo.save(customer);
		
	}

	//to fetch all customers
	@Override
	public List<Customer> getAllCustomer() {
		return customerRepo.findAll();
	}

	//to get a single customer
	@Override
	public Customer getCustomer(Long customerId) {
		return customerRepo.findById(customerId)
	            .orElseThrow(() -> new ResourceNotFoundException("Customer with given id not found on server: " + customerId));
	}
	
	//Update a customer
	@Override
	public Customer updateCustomer(Long customerId, Customer newCustomerData) {
		// Check if the new email already exists for another customer
	    Optional<Customer> existingCustomer = customerRepo.findByEmail(newCustomerData.getEmail());
	    if (existingCustomer.isPresent() && !existingCustomer.get().getCustomerId().equals(customerId)) {
	        throw new InvalidDataException("Email already exists for another customer: " + newCustomerData.getEmail());
	    }
	    
	    return customerRepo.findById(customerId)
	            .map(customer -> {
	                customer.setName(newCustomerData.getName());
	                customer.setEmail(newCustomerData.getEmail());
	                return customerRepo.save(customer);
	            })
	            .orElseThrow(() -> new ResourceNotFoundException("Customer with given id not found: " + customerId));
	}
	
	//Delete a customer along with their associated accounts
	@Override
	public boolean deleteCustomerWithAccount(Long customerId) {
		Optional<Customer> deletedCustomer=customerRepo.findById(customerId);
		
		if (deletedCustomer.isPresent()) {
	        boolean accountsDeleted = deleteAllAccountsByCustomerId(customerId);
//	        System.out.println("Delete step 2");
	        customerRepo.deleteById(customerId);
//	        System.out.println("Delete step 3");
	        return accountsDeleted;
	    }
		else {
			throw new ResourceNotFoundException("Customer with given id not found: " + customerId);
		}
	}
	
	//delete all accounts of given customer id
	private boolean deleteAllAccountsByCustomerId(Long customerId) {
        try {
            Object accDeleted = webClient.delete()
                    .uri("/accounts/customer/{customerId}", customerId)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
            System.out.println("Deleted account "+ accDeleted);
            return true;
        } catch (Exception ex) {
        	System.err.println("Error deleting accounts: " + ex.getMessage());
            return false;
        }
    }
	
	
}
