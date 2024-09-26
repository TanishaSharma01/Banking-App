package com.nagarro.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class MoneyRequestDto {
	@NotNull(message="Customer id cannot be null")
	private Long customerId;
	@NotNull(message="Amount cannot be null")
	private BigDecimal amount;
	@NotNull(message="Email cannot be null")
	@Email(message="Not a valid email")
	private String email;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public MoneyRequestDto(Long customerId, BigDecimal amount, String email) {
		super();
		this.customerId = customerId;
		this.amount = amount;
		this.email=email;
	}
	public MoneyRequestDto() {
		super();
	}
	
}
