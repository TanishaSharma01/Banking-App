package com.nagarro.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nagarro.entities.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Long>{

	Optional<Customer> findByEmail(String email);

}
