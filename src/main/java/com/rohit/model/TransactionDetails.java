package com.rohit.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDetails {
	@Id
	@GeneratedValue
	private long id;
	private long transactionId;
	@OneToOne
	private TrafficViolator violator;
	@OneToOne
	private User paidBy;
	private LocalDateTime dateTime;
}
