package com.rohit.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rohit.dto.AssignHelpRequestDto;
import com.rohit.dto.FineDetails;
import com.rohit.model.SosRequest;
import com.rohit.model.TrafficViolator;
import com.rohit.model.User;
import com.rohit.repository.SosRequestRepository;
import com.rohit.repository.TrafficViolatorRepository;
import com.rohit.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CentralRoomServices {
	private final TrafficViolatorRepository trafficViolatorRepository;
	private final SosRequestRepository sosRequestRepository;
	private final UserRepository userRepository;

	public List<TrafficViolator> getAllTrafficViolatorList() {
		return trafficViolatorRepository.findAll();
	}

	public FineDetails getFineDetails() {
		FineDetails fineDetails = new FineDetails();
		fineDetails.setTotalFine(trafficViolatorRepository.getTotalFineAmount());
		fineDetails.setPendingFine(trafficViolatorRepository.getPendingFineAmount());
		fineDetails.setPaidFine(trafficViolatorRepository.getPaidFineAmount());
		return fineDetails;
	}

	public List<TrafficViolator> getFineList(String filter) {
		if (filter.equals("All"))
			return trafficViolatorRepository.findAll();
		if (filter.equals("Pending"))
			return trafficViolatorRepository.getPendingFineList();
		if (filter.equals("Paid"))
			return trafficViolatorRepository.getPaidFineList();
		else
			return null;
	}

	public List<SosRequest> getSosRequestList(String filter) {

		if (filter.equals("All")) {

			return sosRequestRepository.findAll();

		}
		if (filter.equals("Assigned")) {
			List<SosRequest> assignedStatusSosRequests = sosRequestRepository.getAssignedStatusSosRequests();
			System.out.println(assignedStatusSosRequests);
			return assignedStatusSosRequests;
		}

		if (filter.equals("Pending"))
			return sosRequestRepository.getPendingStatusSosRequests();
		if (filter.equals("Served"))
			return sosRequestRepository.getServedStatusSosRequests();
		else {
			return null;
		}
	}

	public List<User> getTrafficPolice(String filter) {
		if (filter.equals("All"))
			return userRepository.getAllTrafficPolice();
		if (filter.equals("Free"))
			return userRepository.getFreeStatusTrafficPolice();
		if (filter.equals("Assigned"))
			return userRepository.getAssignedStatusTrafficPolice();
		else
			return null;
	}

	public List<User> getFreeTrafficPoliceList(long id) {
		return userRepository.getFreeTrafficPolice(id);
	}

	public boolean assignHelpRequest(AssignHelpRequestDto assignHelpRequestDto) {
		try {
			User user = userRepository.findById(assignHelpRequestDto.getPoliceId()).get();
			SosRequest sosRequest = sosRequestRepository.findById(assignHelpRequestDto.getHelpRequestId()).get();

			user.setStatus("Assigned");
			sosRequest.setServedBy(user);
			sosRequest.setStatus("Assigned");

			userRepository.save(user);
			sosRequestRepository.save(sosRequest);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public User getTrafficPoliceById(long id) {
		try {
			return userRepository.findById(id).get();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<TrafficViolator> searchTrafficViolators(String name) {
		List<TrafficViolator> byNameContaining = trafficViolatorRepository.findByNameStartingWith(name);
		return byNameContaining;
	}

}
