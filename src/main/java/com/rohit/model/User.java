package com.rohit.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String email;
	private String password;
	private String name;
	private String address;
	@Column(name = "aadhar_number")
	private String aadharNumber;
	@Column(name = "aadhar_card_picture")
	private String aadharCardPicture;

	@Column(name = "driving_licence_number")
	private String drivingLicenceNumber;
	@Column(name = "govt_id_card_picture")
	private String govtIdCardPicture;
	private String phone;
	@Column(name = "self_picture")
	private String selfPicture;
	private String role;
	private String status;
	@Column(name = "vehicle_owner_paper_picture")
	private String vehicleOwnerPaperPicture;
	@Column(name = "vehicle_picture")
	private String vehiclePicture;

}
