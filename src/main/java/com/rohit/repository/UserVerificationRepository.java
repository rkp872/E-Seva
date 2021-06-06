package com.rohit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rohit.model.UserVerification;

public interface UserVerificationRepository extends JpaRepository<UserVerification, Long> {
	UserVerification findByToken(String token);
}
