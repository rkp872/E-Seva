package com.rohit.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rohit.dto.MedicalEmergencyDto;
import com.rohit.dto.SosRequestDto;
import com.rohit.dto.TrafficViolatorDto;
import com.rohit.model.MedicalEmergency;
import com.rohit.model.SosRequest;
import com.rohit.model.TrafficViolationTypes;
import com.rohit.model.TrafficViolator;
import com.rohit.model.User;
import com.rohit.repository.MedicalEmergencyRepository;
import com.rohit.repository.PriorityRepository;
import com.rohit.repository.SosRequestRepository;
import com.rohit.repository.TrafficViolationTypesRepository;
import com.rohit.repository.TrafficViolatorRepository;
import com.rohit.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TrafficPoliceServices {
	private final UserRepository userRepository;
	private final TrafficViolationTypesRepository trafficViolationTypesRepository;
	private final TrafficViolatorRepository trafficViolatorRepository;
	private final PriorityRepository priorityRepository;
	private final MedicalEmergencyRepository medicalEmergencyRepository;
	private final SosRequestRepository sosRequestRepository;

	public List<TrafficViolationTypes> getAllTrafficViolationTypes() {

		return trafficViolationTypesRepository.findAll();
	}

	public boolean saveTrafficViolator(TrafficViolatorDto trafficViolatorDto, String email,
			MultipartFile vehicleImage) {
		try {
			User user = userRepository.findByEmail(email);

			TrafficViolator trafficViolator = new TrafficViolator();

			trafficViolator.setName(trafficViolatorDto.getName());
			trafficViolator.setTrafficViolationTypes(
					trafficViolationTypesRepository.findById(trafficViolatorDto.getTrafficViolationTypes()).get());

			trafficViolator.setPhone(trafficViolatorDto.getPhone());
			trafficViolator.setEmail(trafficViolatorDto.getEmail());
			trafficViolator.setDrivingLicence(trafficViolatorDto.getDrivingLicence());
			trafficViolator.setRegistrationNumber(trafficViolatorDto.getRegistrationNumber());
			trafficViolator.setVehicleType(trafficViolatorDto.getVehicleType());
			trafficViolator.setVehiclePicture(vehicleImage.getOriginalFilename());
			trafficViolator.setLocation(trafficViolatorDto.getLocation());
			trafficViolator.setRepeatedOffender(
					trafficViolatorRepository.existTrafficViolator(trafficViolatorDto.getRegistrationNumber()));
			trafficViolator.setDate(LocalDate.now());
			trafficViolator.setOtherInfo(trafficViolatorDto.getOtherInfo());
			trafficViolator.setRegisteredBy(user);
			trafficViolator.setPaymentStatus("Pending");

			// saving image

			trafficViolatorRepository.save(trafficViolator);
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean registerMedicalEmergency(MedicalEmergencyDto medicalEmergencyDto, String email) {
		try {
			User user = userRepository.findByEmail(email);

			MedicalEmergency medicalEmergency = new MedicalEmergency();

			medicalEmergency.setLocation(medicalEmergencyDto.getLocation());
			medicalEmergency.setNumberOfPeopleEffected(medicalEmergencyDto.getNumberOfPeopleEffected());
			medicalEmergency.setPriority(priorityRepository.findByValue(medicalEmergencyDto.getPriority()));
			medicalEmergency.setAccidentType(medicalEmergencyDto.getAccidentType());
			medicalEmergency.setRegisteredBy(user);
			medicalEmergency.setStatus("Pending");

			medicalEmergencyRepository.save(medicalEmergency);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean registerSosRequest(SosRequestDto sosRequestDto, String email) {
		try {
			User user = userRepository.findByEmail(email);

			SosRequest sosRequest = new SosRequest();
			sosRequest.setLocation(sosRequestDto.getLocation());
			sosRequest.setInfo(sosRequestDto.getInfo());
			sosRequest.setRequestedBy(user);
			sosRequest.setStatus("Pending");

			sosRequestRepository.save(sosRequest);
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public SosRequest getAssignedHelpRequest(String email) {
		User user = userRepository.findByEmail(email);
		SosRequest sosRequest = sosRequestRepository.getAssignedHelpRequest(user.getId());
		return sosRequest;
	}

	public boolean workingStatusHelpRequest(long sosId) {
		try {
			SosRequest sosRequest = sosRequestRepository.findById(sosId).get();

			sosRequest.setStatus("Working");

			sosRequestRepository.save(sosRequest);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public boolean doneStatusHelpRequest(long sosId) {
		try {
			SosRequest sosRequest = sosRequestRepository.findById(sosId).get();
			User user = userRepository.findById(sosRequest.getServedBy().getId()).get();
			user.setStatus("FREE");
			sosRequest.setStatus("Served");

			userRepository.save(user);
			sosRequestRepository.save(sosRequest);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
}
