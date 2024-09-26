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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.dto.AccountDetailResponseDto;
import com.nagarro.dto.MoneyRequestDto;
import com.nagarro.entities.Account;
import com.nagarro.exceptions.ResourceNotFoundException;
import com.nagarro.service.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	//create a Account
	@PostMapping
	public ResponseEntity<Account> createAccount(@RequestBody @Validated Account account){
		Account Account1 = accountService.saveAccount(account);
		return ResponseEntity.status(HttpStatus.CREATED).body(Account1);
	}
	
	// Add money to account
    @PostMapping("/add-money/{accountId}")
    public ResponseEntity<String> addMoneyToAccount(@PathVariable String accountId,@RequestBody @Validated MoneyRequestDto request) {
    	accountService.addMoneyToAccount(accountId,request);
    	return new ResponseEntity<>("Money added successfully", HttpStatus.OK);
    	
    }
    
 // Add money to account
    @PostMapping("/withdraw-money/{accountId}")
    public ResponseEntity<String> withdrawMoneyFromAccount(@PathVariable String accountId,@RequestBody @Validated MoneyRequestDto request) {
    	accountService.withdrawMoneyFromAccount(accountId,request);
    	return new ResponseEntity<>("Money withdrawn successfully", HttpStatus.OK);
    	
    }
		
//	//get a single Account
//	@GetMapping("/{accountId}")
//	public ResponseEntity<Account> getSingleAccount(@PathVariable String accountId){
//		Account account1 = accountService.getAccount(accountId);
//		return ResponseEntity.ok(account1);
//	}
	
	// Endpoint to get account details with associated customer details by accountId
    @GetMapping("/{accountId}")
    public ResponseEntity<AccountDetailResponseDto> getAccountDetailsWithCustomer(@PathVariable String accountId) {
        AccountDetailResponseDto detailsResponse = accountService.getAccountDetailsWithCustomer(accountId);

        if (detailsResponse != null) {
            return new ResponseEntity<>(detailsResponse, HttpStatus.OK);
        }else {
        	throw new ResourceNotFoundException("Details could not be fetched: "+accountId);
        }
    }
			
	//get all Accounts
	@GetMapping
	public ResponseEntity<List<Account>> getAllAccount(){
		List<Account> allAccount = accountService.getAllAccount();
		return ResponseEntity.ok(allAccount);
	}

	//delete an account with account Id
	@DeleteMapping("/{accountId}")
    public ResponseEntity<String> deleteAccount(@PathVariable String accountId){
        accountService.deleteAccount(accountId);
        return new ResponseEntity<>("Account deleted successfully", HttpStatus.OK);
    }
	
	@DeleteMapping("/customer/{customerId}")
    public ResponseEntity<String> deleteAccountByCustomerId(@PathVariable Long customerId) {
        boolean accountDeleted = accountService.deleteAccountByCustomerId(customerId);

        if (accountDeleted) {
            return new ResponseEntity<>("Account deleted successfully", HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Account not found for the given customerId: "+customerId);
        }
    }
}
