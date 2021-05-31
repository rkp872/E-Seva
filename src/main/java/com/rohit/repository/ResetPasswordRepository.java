package com.rohit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rohit.model.PasswordReset;

@Repository
public interface ResetPasswordRepository extends JpaRepository<PasswordReset, Long> {
	PasswordReset findByToken(String token);
}
