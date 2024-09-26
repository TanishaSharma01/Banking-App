package com.nagarro.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nagarro.entities.Account;

public interface AccountRepo extends JpaRepository<Account, String>{
	
	void deleteByCustomerId(Long customerId);

}
