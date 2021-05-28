package com.rohit.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MedicalEmergency {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	private String accidentType;
	private String location;
	private int numberOfPeopleEffected;
	@OneToOne
	private Priority priority;
	@OneToOne
	private User registeredBy;
	@OneToOne
	private User servedBy;
	private String status;

}
