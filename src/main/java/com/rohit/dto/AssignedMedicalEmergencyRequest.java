package com.rohit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignedMedicalEmergencyRequest {
	private long medicalEmeregencyId;
	private long ambulanceServiceId;
}
