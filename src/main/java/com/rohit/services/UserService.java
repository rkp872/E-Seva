package com.rohit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rohit.dto.UserDTO;
import com.rohit.model.User;
import com.rohit.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Autowired
	private EmailService emailService;

	public void save(UserDTO userData) {

		User newUser = new User();
		newUser.setEmail(userData.getEmail());
		newUser.setPassword(bcryptEncoder.encode(userData.getPassword()));
		newUser.setName(userData.getName());
		newUser.setAddress(userData.getAddress());
		newUser.setAadharNumber(userData.getAadharNumber());
		newUser.setAadharCardPicture(userData.getAadharCardPicture());
		newUser.setDrivingLicenceNumber(userData.getDrivingLicenceNumber());
		newUser.setGovtIdCardPicture(userData.getGovtIdCardPicture());

		newUser.setPhone(userData.getPhone());

		newUser.setSelfPicture(userData.getSelfPicture());

		newUser.setRole(userData.getRole());

		if (userData.getRole().equals("ROLE_POLICE")) {
			System.out.println("Inside if");
			newUser.setStatus("FREE");
		} else if (userData.getRole().equals("ROLE_AMBULANCE")) {
			System.out.println("Inside else if");
			newUser.setStatus("FREE");
		} else {
			System.out.println("Inside else");
			newUser.setStatus(userData.getStatus());
		}

		newUser.setVehiclePicture(userData.getVehiclePicture());
		newUser.setVehicleOwnerPaperPicture(userData.getVehicleOwnerPaperPicture());

		System.out.println(newUser);

		userRepository.save(newUser);
		emailService.sendEmail("SignUp", "Registration Successful", userData.getEmail());

	}

}
