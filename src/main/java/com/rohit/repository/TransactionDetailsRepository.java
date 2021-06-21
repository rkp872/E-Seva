package com.rohit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rohit.model.TransactionDetails;

public interface TransactionDetailsRepository extends JpaRepository<TransactionDetails, Long> {
	TransactionDetails findByTransactionId(long transactionId);

	@Query("select td from TransactionDetails td where td.violator.email = :email ")
	TransactionDetails getByTrafficViolator(@Param("email") String email);

}
