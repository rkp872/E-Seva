package com.rohit.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "UserTable")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
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
	private boolean isVerified;

}
