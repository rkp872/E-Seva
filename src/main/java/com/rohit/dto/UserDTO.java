package com.rohit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	private String email;
	private String password;
	private String name;
	private String address;

	private String aadharNumber;

	private String aadharCardPicture;

	private String drivingLicenceNumber;

	private String govtIdCardPicture;
	private String phone;

	private String selfPicture;
	private String role;
	private String status;

	private String vehicleOwnerPaperPicture;
	private String vehiclePicture;

}
