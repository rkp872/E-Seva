package com.rohit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rohit.dto.UserDTO;
import com.rohit.helper.RandomStringGenerator;
import com.rohit.model.PasswordReset;
import com.rohit.model.User;
import com.rohit.repository.ResetPasswordRepository;
import com.rohit.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Autowired
	private EmailService emailService;

	@Autowired
	private ResetPasswordRepository resetPasswordRepository;

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

	public boolean forgotPassword(String email) {
		User user = userRepository.findByEmail(email);
		if (user != null) {
			String token = RandomStringGenerator.getAlphaNumericString(8);
			System.out.println("Token : " + token);
			String url = "http://localhost:3000/reset/".concat(token);
			System.out.println("Url : " + url);

			PasswordReset passwordReset = new PasswordReset();
			passwordReset.setUser(user);
			passwordReset.setToken(token);

			resetPasswordRepository.save(passwordReset);

			emailService.sendEmail("Forgot Password", "Click " + url + " to reset your password", email);
			return true;
		} else {
			return false;
		}
	}

	public boolean resetPassword(String newPassword, String token) {
		PasswordReset passwordReset = resetPasswordRepository.findByToken(token);
		if (passwordReset != null) {
			User user = passwordReset.getUser();
			System.out.println(user);
			user.setPassword(bcryptEncoder.encode(newPassword));
			userRepository.save(user);
			resetPasswordRepository.delete(passwordReset);
			emailService.sendEmail("Reset Password", "Your password has been updated successfully", user.getEmail());
			return true;
		} else {
			return false;
		}
	}

}
