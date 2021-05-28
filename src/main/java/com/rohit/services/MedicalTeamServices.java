package com.rohit.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rohit.dto.AssignedMedicalEmergencyRequest;
import com.rohit.model.MedicalEmergency;
import com.rohit.model.User;
import com.rohit.repository.MedicalEmergencyRepository;
import com.rohit.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class MedicalTeamServices {
	private final MedicalEmergencyRepository medicalEmergencyRepository;
	private final UserRepository userRepository;

	public List<MedicalEmergency> getAllMedicalEmergency(String filter) {
		if (filter.equals("All"))
			return medicalEmergencyRepository.findAll();
		if (filter.equals("Assigned"))
			return medicalEmergencyRepository.getAssignedStatusMedicalEmergency();
		if (filter.equals("Pending"))
			return medicalEmergencyRepository.getPendingStatusAssignedMedicalEmergency();
		if (filter.equals("Served"))
			return medicalEmergencyRepository.getServedStatusMedicalEmergency();
		else {
			return null;
		}

	}

	public List<User> getAmbulance(String filter) {
		if (filter.equals("All"))
			return userRepository.getAllAmbulance();
		if (filter.equals("Assigned"))
			return userRepository.getAssignedAmbulance();
		if (filter.equals("Free"))
			return userRepository.getFreeAmbulance();
		else
			return null;
	}

	public boolean assignMedicalEmergency(AssignedMedicalEmergencyRequest assignedMedicalEmergencyRequest) {
		try {
			User user = userRepository.findById(assignedMedicalEmergencyRequest.getAmbulanceServiceId()).get();
			MedicalEmergency medicalEmergency = medicalEmergencyRepository
					.findById(assignedMedicalEmergencyRequest.getMedicalEmeregencyId()).get();

			user.setStatus("Assigned");
			medicalEmergency.setServedBy(user);
			medicalEmergency.setStatus("Assigned");

			userRepository.save(user);
			medicalEmergencyRepository.save(medicalEmergency);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
}
