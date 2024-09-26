package com.nagarro.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
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
public class Customer {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long customerId;
	
	@NotNull(message="Name cannot be null!")
	@Column(length=20)
	private String name;
	
	@NotNull(message="Email cannot be null!")
	@Column(length = 30, unique = true)
	@Email(message="Not a valid email")
    private String email;
}
