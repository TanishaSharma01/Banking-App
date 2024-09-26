package com.nagarro.entities;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class Account {
	
	@Id
	public String accountId;
	
	@NotNull(message="Customer id cannot be null")
	public Long customerId;
	
	@NotNull(message="Balance cannot be null")
	public BigDecimal balance;
	
}
