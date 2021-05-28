package com.rohit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rohit.model.SosRequest;

public interface SosRequestRepository extends JpaRepository<SosRequest, Long> {

	@Query("select s from SosRequest s where s.servedBy.id=:id and s.status!='Served'")
	SosRequest getAssignedHelpRequest(@Param("id") long id);

	@Query("select s from SosRequest s where s.status='Assigned'")
	List<SosRequest> getAssignedStatusSosRequests();

	@Query("select s from SosRequest s where s.status='Pending'")
	List<SosRequest> getPendingStatusSosRequests();

	@Query("select s from SosRequest s where s.status='Served'")
	List<SosRequest> getServedStatusSosRequests();
}
