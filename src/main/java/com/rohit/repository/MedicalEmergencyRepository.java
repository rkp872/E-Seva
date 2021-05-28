package com.rohit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rohit.model.MedicalEmergency;

public interface MedicalEmergencyRepository extends JpaRepository<MedicalEmergency, Long> {
	@Query("select s from MedicalEmergency s where s.servedBy.id=:id and s.status!='Served'")
	MedicalEmergency getAssignedMedicalEmergency(@Param("id") long id);

	@Query("select s from MedicalEmergency s where  s.status='Pending'")
	List<MedicalEmergency> getPendingStatusAssignedMedicalEmergency();

	@Query("select s from MedicalEmergency s where  s.status='Served'")
	List<MedicalEmergency> getServedStatusMedicalEmergency();

	@Query("select s from MedicalEmergency s where  s.status='Assigned'")
	List<MedicalEmergency> getAssignedStatusMedicalEmergency();

}
