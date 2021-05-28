package com.rohit.controller;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rohit.helper.Message;
import com.rohit.model.MedicalEmergency;
import com.rohit.services.AmbulanceDriverServices;

import lombok.AllArgsConstructor;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/ambulance-service")
public class AmbulanceDriverController {

	private final AmbulanceDriverServices ambulanceDriverServices;

	@GetMapping("/get-assigned-medical-emergency")
	public MedicalEmergency getAssignedMedicalEmergency(Principal principal) {
		return ambulanceDriverServices.getAssignedMedicalEmergency(principal.getName());
	}

	@GetMapping("/working-status-medical-emergency/{id}")
	public ResponseEntity<Message> workingStatusMedicalEmergency(@PathVariable("id") long id) {
		if (ambulanceDriverServices.workingStatusMedicalEmergency(id)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Message("Medical Emergency Status Changed", "success"));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Message("Medical Emergency Status Has Not changed Try Again !!", "danger"));
		}

	}

	@GetMapping("/done-status-medical-emergency/{id}")
	public ResponseEntity<Message> doneStatusMedicalEmergency(@PathVariable("id") long id) {
		if (ambulanceDriverServices.doneStatusMedicalEmergency(id)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Message("Medical Emergency Served Successfully", "success"));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Message("Medical Emergency Status Has Not changed Try Again !!", "danger"));
		}

	}
}
