package com.nagarro.dto;

import com.nagarro.entities.Account;

public class AccountDetailResponseDto {
	private Account account;
	private CustomerDto customerDto;
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public CustomerDto getCustomer() {
		return customerDto;
	}
	public void setCustomer(CustomerDto customerDto) {
		this.customerDto = customerDto;
	}
	
	public AccountDetailResponseDto(Account account, CustomerDto customerDto) {
		super();
		this.account = account;
		this.customerDto = customerDto;
	}
	public AccountDetailResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
