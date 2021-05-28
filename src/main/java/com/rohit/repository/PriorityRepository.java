package com.rohit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rohit.model.Priority;

public interface PriorityRepository extends JpaRepository<Priority, Long> {
	Priority findByValue(String value);
}
