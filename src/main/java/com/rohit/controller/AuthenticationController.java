package com.rohit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rohit.config.CustomUserDetailsService;
import com.rohit.config.JwtUtil;
import com.rohit.dto.AuthenticationRequest;
import com.rohit.dto.AuthenticationResponse;
import com.rohit.dto.UserDTO;
import com.rohit.helper.Message;
import com.rohit.model.User;
import com.rohit.repository.UserRepository;
import com.rohit.services.UserService;

@CrossOrigin
@RestController
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
					authenticationRequest.getPassword()));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new Message("Invalid Credentials", "danger"));
		}

		UserDetails userdetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
		String token = jwtUtil.generateToken(userdetails);
		User user = userRepository.findByEmail(authenticationRequest.getEmail());

		if (user.isVerified()) {
			return ResponseEntity.ok(new AuthenticationResponse(token, user));
		} else {
			return ResponseEntity.status(HttpStatus.LOCKED)
					.body(new Message("Your account is not verified yet", "danger"));
		}

		// return ResponseEntity.ok(new AuthenticationResponse(token, user.getRole()));

	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<Message> saveUser(UserDTO user) throws Exception {

		userService.save(user);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new Message("Registered Successfully, Check your mail to verify your account", "success"));
	}

	@GetMapping("/verify/{token}")
	public ResponseEntity<Message> verifyUser(@PathVariable("token") String token) {
		if (userService.verifyUser(token)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Message("Successfully verified! Login Now", "success"));
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(new Message("Invalid Link Try Again!!", "danger"));
		}
	}

	@PostMapping("/forgot")
	public ResponseEntity<Message> forgotPassword(String email) {
		if (userService.forgotPassword(email)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Message("Check your mail to reset your password", "success"));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new Message("Email entered is not found", "danger"));
		}
	}

	@PostMapping("/reset/{token}")
	public ResponseEntity<Message> resetPassword(String password, @PathVariable("token") String token) {
		System.out.println("Token : " + token);
		System.out.println("Password  : " + password);
		if (userService.resetPassword(password, token)) {
			return ResponseEntity.status(HttpStatus.OK).body(new Message("Passord updated successfully", "success"));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new Message("Link is expired, Try again !!", "danger"));
		}
	}
}
