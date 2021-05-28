package com.rohit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rohit.model.TrafficViolator;

public interface TrafficViolatorRepository extends JpaRepository<TrafficViolator, Long> {

	@Query("SELECT COUNT(t) > 0 FROM TrafficViolator t WHERE t.registrationNumber = :registrationNumber")
	Boolean existTrafficViolator(@Param("registrationNumber") String registrationNumber);

	@Query("select sum(tv.trafficViolationTypes.fineAmount) from TrafficViolator tv ")
	int getTotalFineAmount();

	@Query("select sum(tv.trafficViolationTypes.fineAmount) from TrafficViolator tv where tv.paymentStatus='Pending'")
	int getPendingFineAmount();

	@Query("select sum(tv.trafficViolationTypes.fineAmount) from TrafficViolator tv where tv.paymentStatus='Paid'")
	int getPaidFineAmount();

	@Query("select tv from TrafficViolator tv where tv.paymentStatus='Pending'")
	List<TrafficViolator> getPendingFineList();

	@Query("select tv from TrafficViolator tv where tv.paymentStatus='Paid'")
	List<TrafficViolator> getPaidFineList();

	List<TrafficViolator> findByEmail(String email);

	List<TrafficViolator> findByDrivingLicence(String drivingLicence);

	List<TrafficViolator> findByRegistrationNumber(String registrationNumber);

	List<TrafficViolator> findByNameContaining(String regEx);

}
