package com.rohit.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rohit.dto.UserDTO;
import com.rohit.model.User;
import com.rohit.repository.UserRepository;
import com.rohit.services.EmailService;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userDao;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Autowired
	private EmailService emailService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<SimpleGrantedAuthority> roles = null;

		User user = userDao.findByEmail(username);
		if (user != null) {
			System.out.println(user.getRole());
			roles = Arrays.asList(new SimpleGrantedAuthority(user.getRole()));
			System.out.println(roles);
			return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), roles);
		}
		throw new UsernameNotFoundException("User not found with the name " + username);
	}

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

		userDao.save(newUser);
		emailService.sendEmail("SignUp", "Registration Successful", user.getEmail());

	}

}
