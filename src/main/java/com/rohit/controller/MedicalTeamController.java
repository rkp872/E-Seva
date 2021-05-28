package com.rohit.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rohit.dto.AssignedMedicalEmergencyRequest;
import com.rohit.helper.Message;
import com.rohit.model.MedicalEmergency;
import com.rohit.model.User;
import com.rohit.services.MedicalTeamServices;

import lombok.AllArgsConstructor;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/medical-team")
public class MedicalTeamController {
	private final MedicalTeamServices medicalTeamServices;

	@GetMapping("/home")
	public String home() {
		return "Medical Team";
	}

	@GetMapping("/get-medical-emergency/{filter}")
	public List<MedicalEmergency> getAllMedicalEmergency(@PathVariable("filter") String filter) {
		return medicalTeamServices.getAllMedicalEmergency(filter);
	}

	@GetMapping("/get-ambulance/{filter}")
	public List<User> getFreeAmbulance(@PathVariable("filter") String filter) {
		return medicalTeamServices.getAmbulance(filter);
	}

	@PostMapping("/assign-medical-emergency")
	public ResponseEntity<Message> assignMedicalEmergency(
			@RequestBody AssignedMedicalEmergencyRequest assignedMedicalEmergencyRequest) {
		if (medicalTeamServices.assignMedicalEmergency(assignedMedicalEmergencyRequest)) {
			return ResponseEntity.status(HttpStatus.OK).body(new Message("Assigned Successfully", "success"));
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(new Message("Assignment Failed Try Again !!", "danger"));
		}

	}
}
