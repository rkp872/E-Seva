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

	public void save(UserDTO user) {

		User newUser = new User();
		newUser.setEmail(user.getEmail());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		newUser.setName(user.getName());
		newUser.setAddress(user.getAddress());
		newUser.setAadharNumber(user.getAadharNumber());
		newUser.setAadharCardPicture(user.getAadharCardPicture());
		newUser.setDrivingLicenceNumber(user.getDrivingLicenceNumber());
		newUser.setGovtIdCardPicture(user.getGovtIdCardPicture());

		newUser.setPhone(user.getPhone());

		newUser.setSelfPicture(user.getSelfPicture());

		newUser.setRole(user.getRole());

		if (user.getRole().equals("ROLE_POLICE")) {
			System.out.println("Inside if");
			newUser.setStatus("FREE");
		} else if (user.getRole().equals("ROLE_AMBULANCE")) {
			System.out.println("Inside else if");
			newUser.setStatus("FREE");
		} else {
			System.out.println("Inside else");
			newUser.setStatus(user.getStatus());
		}

		newUser.setVehiclePicture(user.getVehiclePicture());
		newUser.setVehicleOwnerPaperPicture(user.getVehicleOwnerPaperPicture());

		System.out.println(newUser);

		userRepository.save(newUser);
		emailService.sendEmail("SignUp", "Registration Successful", user.getEmail());

	}

}
