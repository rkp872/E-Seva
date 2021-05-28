package com.rohit.services;

import org.springframework.stereotype.Service;

import com.rohit.model.MedicalEmergency;
import com.rohit.model.User;
import com.rohit.repository.MedicalEmergencyRepository;
import com.rohit.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AmbulanceDriverServices {

	private final UserRepository userRepository;
	private final MedicalEmergencyRepository medicalEmergencyRepository;

	public MedicalEmergency getAssignedMedicalEmergency(String email) {
		User user = userRepository.findByEmail(email);

		MedicalEmergency medicalEmergency = medicalEmergencyRepository.getAssignedMedicalEmergency(user.getId());

		return medicalEmergency;

	}

	public boolean workingStatusMedicalEmergency(long id) {
		try {
			MedicalEmergency medicalEmergency = medicalEmergencyRepository.findById(id).get();

			medicalEmergency.setStatus("Working");

			medicalEmergencyRepository.save(medicalEmergency);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean doneStatusMedicalEmergency(long id) {
		try {
			MedicalEmergency medicalEmergency = medicalEmergencyRepository.findById(id).get();
			User user = userRepository.findById(medicalEmergency.getServedBy().getId()).get();

			medicalEmergency.setStatus("Served");
			user.setStatus("FREE");
			userRepository.save(user);
			medicalEmergencyRepository.save(medicalEmergency);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

}
