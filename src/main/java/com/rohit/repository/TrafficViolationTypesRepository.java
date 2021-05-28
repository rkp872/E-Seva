package com.rohit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rohit.model.TrafficViolationTypes;

public interface TrafficViolationTypesRepository extends JpaRepository<TrafficViolationTypes, Long> {

}
