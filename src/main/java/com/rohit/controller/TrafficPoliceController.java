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
import org.springframework.web.multipart.MultipartFile;

import com.rohit.dto.MedicalEmergencyDto;
import com.rohit.dto.SosRequestDto;
import com.rohit.dto.TrafficViolatorDto;
import com.rohit.helper.Message;
import com.rohit.model.SosRequest;
import com.rohit.model.TrafficViolationTypes;
import com.rohit.services.TrafficPoliceServices;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/traffic-police")
public class TrafficPoliceController {

	private final TrafficPoliceServices trafficPoliceServices;

	@GetMapping("/getAllTrafficViolationTypes")
	public List<TrafficViolationTypes> getAllTrafficViolationTypes() {

		return trafficPoliceServices.getAllTrafficViolationTypes();
	}

	@PostMapping("/register-traffic-violator")
	public ResponseEntity<Message> registerTrafficViolator(TrafficViolatorDto trafficViolatorDto,
			@RequestParam("vehiclePhoto") MultipartFile vehicleImage, Principal principal) {

		if (trafficPoliceServices.saveTrafficViolator(trafficViolatorDto, principal.getName(), vehicleImage)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Message("Traffic Violator Registered Successfully", "success"));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Message("Traffic Violator Registration Failed Try Again !!", "danger"));
		}

	}

	@PostMapping("/register-medical-emergency")
	public ResponseEntity<Message> registerMedicalEmergency(@RequestBody MedicalEmergencyDto medicalEmergencyDto,
			Principal principal) {
		if (trafficPoliceServices.registerMedicalEmergency(medicalEmergencyDto, principal.getName())) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Message("Medical Emergency Registered Successfully", "success"));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Message("Medical Emergency Registration Failed Try Again !!", "danger"));
		}

	}

	@PostMapping("/register-sos-request")
	public ResponseEntity<Message> registerSosRequest(@RequestBody SosRequestDto sosRequestDto, Principal principal) {
		if (trafficPoliceServices.registerSosRequest(sosRequestDto, principal.getName())) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Message("SOS Request Registered Successfully", "success"));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Message("SOS Request Registration Failed Try Again !!", "danger"));
		}

	}

	@GetMapping("/get-assigned-help-request")
	public SosRequest getAssignedHelpRequest(Principal principal) {
		return trafficPoliceServices.getAssignedHelpRequest(principal.getName());
	}

	@GetMapping("/working-status-help-request/{sosId}")
	public ResponseEntity<Message> workingStatusHelpRequest(@PathVariable("sosId") long sosId) {

		if (trafficPoliceServices.workingStatusHelpRequest(sosId)) {
			return ResponseEntity.status(HttpStatus.OK).body(new Message("Help Request Status Changed", "success"));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Message("Help Request Status Has Not changed Try Again !!", "danger"));
		}

	}

	@GetMapping("/done-status-help-request/{sosId}")
	public ResponseEntity<Message> doneStatusHelpRequest(@PathVariable("sosId") long sosId) {
		if (trafficPoliceServices.doneStatusHelpRequest(sosId)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Message("Help Request Served Successfully", "success"));
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Message("Help Request Status Has Not changed Try Again !!", "danger"));
		}

	}
}
