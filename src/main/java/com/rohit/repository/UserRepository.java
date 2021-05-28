package com.rohit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rohit.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

	@Query("select u from User u where u.role='ROLE_POLICE' and status='FREE' and u.id != :id")
	List<User> getFreeTrafficPolice(@Param("id") Long id);

	@Query("select u from User u where u.role='ROLE_AMBULANCE' and status='FREE' ")
	List<User> getFreeAmbulance();

	@Query("select u from User u where u.role='ROLE_AMBULANCE' and status='Assigned' ")
	List<User> getAssignedAmbulance();

	@Query("select u from User u where u.role='ROLE_AMBULANCE' ")
	List<User> getAllAmbulance();

	@Query("select u from User u where u.role='ROLE_POLICE'")
	List<User> getAllTrafficPolice();

	@Query("select u from User u where u.role='ROLE_POLICE' and status='FREE'")
	List<User> getFreeStatusTrafficPolice();

	@Query("select u from User u where u.role='ROLE_POLICE' and status='Assigned' ")
	List<User> getAssignedStatusTrafficPolice();
}
