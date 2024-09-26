package com.nagarro.service;

import java.util.List;

import com.nagarro.dto.AccountDetailResponseDto;
import com.nagarro.dto.MoneyRequestDto;
import com.nagarro.entities.Account;

public interface AccountService {
	Account saveAccount(Account account);
	
    List<Account> getAllAccount();
	
//	Account getAccount(String accountId);
    AccountDetailResponseDto getAccountDetailsWithCustomer(String accountId);
    
    void addMoneyToAccount(String accountId, MoneyRequestDto addMoneyRequest);
	
    void withdrawMoneyFromAccount(String accountId, MoneyRequestDto addMoneyRequest);
    
	void deleteAccount(String accountId);
	
	boolean deleteAccountByCustomerId(Long customerId);
}
