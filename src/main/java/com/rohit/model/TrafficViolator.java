package com.rohit.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrafficViolator {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	private String name;
	@OneToOne
	private TrafficViolationTypes trafficViolationTypes;
	private String phone;
	private String email = "NA";
	private String drivingLicence;
	private String registrationNumber;
	private String vehicleType;
	private String vehiclePicture;
	private String location;
	private boolean isRepeatedOffender;
	private LocalDate date;
	private String otherInfo = "No information";
	@OneToOne
	private User registeredBy;
	private String paymentStatus;
}
