package com.nagarro.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.nagarro.dto.AccountDetailResponseDto;
import com.nagarro.dto.CustomerDto;
import com.nagarro.dto.MoneyRequestDto;
import com.nagarro.entities.Account;
import com.nagarro.exceptions.InvalidDataException;
import com.nagarro.exceptions.ResourceNotFoundException;
import com.nagarro.repo.AccountRepo;
import com.nagarro.service.AccountService;

import jakarta.transaction.Transactional;

@Service
public class AccountServiceImpl implements AccountService{
	
    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

	@Autowired
	private AccountRepo accountRepo;
	
	private final WebClient webClient;
    private final String customerServiceBaseUrl;
    
    public AccountServiceImpl(WebClient.Builder webClientBuilder, @Value("${customer.service.base.url}") String customerServiceBaseUrl) {
        this.webClient = webClientBuilder.baseUrl(customerServiceBaseUrl).build();
        this.customerServiceBaseUrl = customerServiceBaseUrl;
    }

    //create account
	@Override
	public Account saveAccount(Account account) {
		Long customerId = account.getCustomerId();
		//if valid customer id
		if(validateCustomer(customerId)) {
			BigDecimal balance = account.getBalance();
			//if balance not equal to null and greater than or equal to 100
			if (balance != null && balance.compareTo(BigDecimal.valueOf(100)) >= 0) {
				String randomAccountId = UUID.randomUUID().toString();
				account.setAccountId(randomAccountId);
				return accountRepo.save(account);
			}
			else {
				throw new InvalidDataException("Balance should be more or equal to 100 when creating an account");
			}
		}
		else {
			throw new ResourceNotFoundException("Customer id not found: "+customerId);
		}
		
	}

	//get all accounts
	@Override
	public List<Account> getAllAccount() {
		return accountRepo.findAll();
	}

	//get account and related customer details by id
	@Override
	public AccountDetailResponseDto getAccountDetailsWithCustomer(String accountId) {
		//find account
		Optional<Account> accountOptional = accountRepo.findById(accountId);
	    //if account present then get details
		if (accountOptional.isPresent()) {
	        Account account = accountOptional.get();
	        Long customerId = account.getCustomerId();
	        CustomerDto customerDto = getCustomerDetails(customerId);
	        return new AccountDetailResponseDto(account, customerDto);
	    } else {
	        throw new ResourceNotFoundException("Account with given id not found: " + accountId);
	    }
	}
	
	
	//delete account by account id
	@Override
	public void deleteAccount(String accountId) {
		//find account
		Optional<Account> deletedAccount = accountRepo.findById(accountId);
		//if account present then delete account
		if(deletedAccount.isPresent()) {
			accountRepo.deleteById(accountId);
		}
		else {
			throw new ResourceNotFoundException("Account with given id not found: "+ accountId);
		}
	}

	//Delete account by customerId
    @Transactional
    public boolean deleteAccountByCustomerId(Long customerId) {
        try {
            accountRepo.deleteByCustomerId(customerId);
            return true;
        } catch (Exception ex) {
            logger.error("Error deleting account by customerId: {}", customerId, ex);
            return false;
        }
    }
    
    //Deposit money in account
    @Override
    public void addMoneyToAccount(String accountId, MoneyRequestDto addMoneyRequest){
    	//get account by provided account id
    	Optional<Account> accountOptional = accountRepo.findById(accountId);
    	//if account not null
    	if(accountOptional.isPresent()) {
    		Account account = accountOptional.get();
	        Long customerId = account.getCustomerId();
	        
	        //if customer id of account matches that we provided in request
	        if(customerId==addMoneyRequest.getCustomerId()) {
	        	
	        	CustomerDto customerDto=getCustomerDetails(customerId);
	        	BigDecimal amount = addMoneyRequest.getAmount();
	        	String email=addMoneyRequest.getEmail();
	        	//if email of customer matches that we provided in request
	        	if(email.equals(customerDto.getEmail())) {
	        		BigDecimal currentBalance = account.getBalance();
	        		BigDecimal newBalance = currentBalance.add(amount);
	        		
	        		if(amount.compareTo(BigDecimal.ZERO) > 0) {
	        			account.setBalance(newBalance);
		        		accountRepo.save(account); // Save the updated account
		        		System.out.println("Amount added to account");
	        		}
	        		else {
	        			throw new InvalidDataException("Enter a valid deposit amount!");
	        		}
	        	}
	        	else {
	        		System.out.println("Email does not match!");
	            	throw new InvalidDataException("Email does not match!");
	        	}
	        }
	        else {
	        	System.out.println("Customer id does not match");
	        	throw new InvalidDataException("Customer id does not match");
	        }
    	}else {
        	throw new ResourceNotFoundException("Account with given id not found: " + accountId);

    	}
    }
    
    //Withdraw money from account
    @Override
    public void withdrawMoneyFromAccount(String accountId, MoneyRequestDto addMoneyRequest){
    	//get account by provided account id
    	Optional<Account> accountOptional = accountRepo.findById(accountId);
    	//if account not null
    	if(accountOptional.isPresent()) {
    		Account account = accountOptional.get();
	        Long customerId = account.getCustomerId();
	        
	        //if customer id of account matches that we provided in request
	        if(customerId==addMoneyRequest.getCustomerId()) {
	        	
	        	CustomerDto customerDto=getCustomerDetails(customerId);
	        	BigDecimal amount = addMoneyRequest.getAmount();
	        	String email=addMoneyRequest.getEmail();
	        	//if email of customer matches that we provided in request
	        	if(email.equals(customerDto.getEmail())) {
	        		
	        		BigDecimal currentBalance = account.getBalance();
	        		BigDecimal newBalance = currentBalance.subtract(amount);
	        		
	        		if(amount.compareTo(BigDecimal.ZERO) > 0) {
	        			// Check if new balance is not negative
		                if (newBalance.compareTo(BigDecimal.ZERO) >= 0) {
		                    account.setBalance(newBalance);
		                    accountRepo.save(account); // Save the updated account
		                    System.out.println("Amount withdrawn from account");
		                } else {
		                    System.out.println("Insufficient balance!");
		                    throw new InvalidDataException("Insufficient balance!");
		                }
	        		}else {
	        			throw new InvalidDataException("Enter a valid withdraw amount!");
	        		}
	        	}
	        	else {
	        		System.out.println("Email does not match!");
	            	throw new InvalidDataException("Email does not match!");
	        	}
	        }
	        else {
	        	System.out.println("Customer id does not match");
	        	throw new InvalidDataException("Customer id does not match");
	        }
    	}else {
        	throw new ResourceNotFoundException("Account with given id not found: " + accountId);

    	}
    }
    
    
	// Fetch customer details by customerId
    private CustomerDto getCustomerDetails(Long customerId) {
        try {
            return webClient.get()
                    .uri("/customers/{customerId}", customerId)
                    .retrieve()
                    .bodyToMono(CustomerDto.class)
                    .block();
        } catch (Exception ex) {
            return null;
        }
    }
    
	//to validate customer
	private boolean validateCustomer(Long customerId) {
		return getCustomerDetails(customerId)!=null;
	}
}
