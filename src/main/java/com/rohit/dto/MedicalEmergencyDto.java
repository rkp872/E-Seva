package com.rohit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalEmergencyDto {
	private String accidentType;
	private String location;
	private int numberOfPeopleEffected;
	private String priority;
}
