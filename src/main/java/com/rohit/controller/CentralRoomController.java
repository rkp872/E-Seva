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

import com.rohit.dto.AssignHelpRequestDto;
import com.rohit.dto.FineDetails;
import com.rohit.helper.Message;
import com.rohit.model.SosRequest;
import com.rohit.model.TrafficViolator;
import com.rohit.model.User;
import com.rohit.services.CentralRoomServices;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/central-room")
public class CentralRoomController {
	private final CentralRoomServices centralRoomService;

	@GetMapping("/home")
	public String home() {
		return "Central Room";
	}

	@GetMapping("/get-traffic-violator-list")
	public List<TrafficViolator> getAllTrafficViolator() {
		return centralRoomService.getAllTrafficViolatorList();
	}

	@GetMapping("/fine-details")
	public FineDetails getTotalFine() {
		return centralRoomService.getFineDetails();
	}

	@GetMapping("/fine-list/{filter}")
	public List<TrafficViolator> getPendingFineList(@PathVariable("filter") String filter) {
		System.out.println("Filelist controller");
		return centralRoomService.getFineList(filter);
	}

	@GetMapping("/help-request-list/{filter}")
	public List<SosRequest> getAllHelpRequests(@PathVariable("filter") String filter) {
		System.out.println("Inside sos controillet : " + filter);
		return centralRoomService.getSosRequestList(filter);
	}

	@GetMapping("/free-traffic-police-list/{id}")
	public List<User> getFreeTrafficPoliceList(@PathVariable("id") long id) {
		return centralRoomService.getFreeTrafficPoliceList(id);
	}

	@GetMapping("get-traffic-police/{filter}")
	public List<User> getTrafficPolice(@PathVariable("filter") String filter) {
		return centralRoomService.getTrafficPolice(filter);
	}

	@PostMapping("/assign-help-request")
	public ResponseEntity<Message> assignHelpRequest(@RequestBody AssignHelpRequestDto assignHelpRequestDto) {
		if (centralRoomService.assignHelpRequest(assignHelpRequestDto)) {
			return ResponseEntity.status(HttpStatus.OK).body(new Message("Assigned Successfully", "success"));
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(new Message("Assignment Failed Try Again !!", "danger"));
		}
	}

	@GetMapping("/get-traffic-police-by-id/{id}")
	public User getTrafficPoliceById(@PathVariable("id") long id) {
		return centralRoomService.getTrafficPoliceById(id);
	}

	@GetMapping("/search-traffic-violator/{name}")
	public List<TrafficViolator> searchTrafficViolator(@PathVariable("name") String name) {
		return centralRoomService.searchTrafficViolators(name);
	}
}
