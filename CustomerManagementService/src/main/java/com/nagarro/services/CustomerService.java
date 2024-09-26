package com.nagarro.services;

import java.util.List;

import com.nagarro.entities.Customer;

public interface CustomerService {
	
	Customer saveCustomer(Customer customer);
	
	List<Customer> getAllCustomer();
	
	Customer getCustomer(Long customerId);
	
	boolean deleteCustomerWithAccount(Long customerId);
	
	Customer updateCustomer(Long customerId, Customer newCustomerData);
}
