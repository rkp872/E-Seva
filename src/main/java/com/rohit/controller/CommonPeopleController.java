package com.rohit.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rohit.dto.MedicalEmergencyDto;
import com.rohit.helper.Message;
import com.rohit.model.TrafficViolator;
import com.rohit.model.TransactionDetails;
import com.rohit.services.CommonPeopleServices;

import lombok.AllArgsConstructor;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/common-people")
public class CommonPeopleController {

	private final CommonPeopleServices commonPeopleServices;

	@GetMapping("/my-offense/{filter}")
	public List<TrafficViolator> searchByEmail(@PathVariable("filter") String filter, Principal principal) {
		return commonPeopleServices.getOwnTrafficViolation(principal.getName(), filter);
	}

	@PostMapping("/traffic-violator-by-email")
	public List<TrafficViolator> searchByEmail(@RequestParam("email") String email) {
		return commonPeopleServices.searchTrafficViolationByEmail(email);
	}

	@PostMapping("/traffic-violator-by-dl")
	public List<TrafficViolator> searchByDrivingLicenceNumber(
			@RequestParam("drivingLicenceNumber") String drivingLicenceNumber) {
		return commonPeopleServices.searchByDrivingLicence(drivingLicenceNumber);
	}

	@PostMapping("/traffic-violator-by-reg")
	public List<TrafficViolator> searchByRegistrationNumber(@RequestParam("regNumber") String regNumber) {
		return commonPeopleServices.seachByRegistrationNumber(regNumber);
	}

	@PostMapping("/fine-payment")
	public TransactionDetails finePayment(@RequestParam("violatorid") long violatorId, Principal principal) {
		return commonPeopleServices.finePaymet(principal.getName(), violatorId);

	}

	@GetMapping("/track-payment/{transactionId}")
	public TransactionDetails trackPayment(@PathVariable("transactionId") long transactionId) {
		return commonPeopleServices.trackPayment(transactionId);
	}

	@PostMapping("/register-medical-emergency")
	public ResponseEntity<Message> registerMedicalEmergency(@RequestBody MedicalEmergencyDto medicalEmergencyDto,
			Principal principal) {
		if (commonPeopleServices.registerMedicalEmergency(medicalEmergencyDto, principal.getName())) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Message("Medical Emergency Registered Successfully", "success"));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Message("Medical Emergency Registration Failed Try Again !!", "danger"));
		}

	}

}
