package com.rohit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrafficViolatorDto {

	private String name;
	private Long trafficViolationTypes;
	private String phone;
	private String email = "NA";
	private String drivingLicence;
	private String registrationNumber;
	private String vehicleType;
	private String vehiclePicture;
	private String location;
	// private boolean isRepeatedOffender;
	private String otherInfo;
}
