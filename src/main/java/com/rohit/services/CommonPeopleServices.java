package com.rohit.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.rohit.dto.MedicalEmergencyDto;
import com.rohit.model.MedicalEmergency;
import com.rohit.model.TrafficViolator;
import com.rohit.model.TransactionDetails;
import com.rohit.model.User;
import com.rohit.repository.MedicalEmergencyRepository;
import com.rohit.repository.PriorityRepository;
import com.rohit.repository.TrafficViolatorRepository;
import com.rohit.repository.TransactionDetailsRepository;
import com.rohit.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommonPeopleServices {
	private final UserRepository userRepository;
	private final TrafficViolatorRepository trafficViolatorRepository;
	private final TransactionDetailsRepository transactionDetailsRepository;
	private final PriorityRepository priorityRepository;
	private final MedicalEmergencyRepository medicalEmergencyRepository;

	public List<TrafficViolator> getOwnTrafficViolation(String email, String filter) {
		if (filter.equals("All"))
			return trafficViolatorRepository.findByEmail(email);
		if (filter.equals("Pending"))
			return trafficViolatorRepository.getMyPendingOffence(email);
		if (filter.equals("Paid"))
			return trafficViolatorRepository.getMyPaidOffence(email);
		else
			return null;
	}

	public List<TrafficViolator> searchTrafficViolationByEmail(String email) {
		return trafficViolatorRepository.findByEmail(email);
	}

	public List<TrafficViolator> searchByDrivingLicence(String drivingLicence) {
		return trafficViolatorRepository.findByDrivingLicence(drivingLicence);
	}

	public List<TrafficViolator> seachByRegistrationNumber(String regNumber) {
		return trafficViolatorRepository.findByRegistrationNumber(regNumber);
	}

	public boolean finePaymet(String email, long violatorId) {

		try {
			User user = userRepository.findByEmail(email);
			TrafficViolator trafficViolator = trafficViolatorRepository.findById(violatorId).get();
			trafficViolator.setPaymentStatus("Paid");
			trafficViolatorRepository.save(trafficViolator);
			TransactionDetails transactionDetails = new TransactionDetails();
			transactionDetails.setPaidBy(user);
			transactionDetails.setViolator(trafficViolator);
			transactionDetails.setDateTime(LocalDateTime.now());
			String hashInp = trafficViolator.getName() + trafficViolator.getTrafficViolationTypes().getOffence()
					+ user.getName();

			long trId = hashInp.hashCode();
			if (trId < 0)
				trId = -trId;
			transactionDetails.setTransactionId(trId);

			transactionDetailsRepository.save(transactionDetails);

			return true;

		} catch (Exception e) {
			return false;
		}

	}

	public TransactionDetails trackPayment(long transactionId) {
		return transactionDetailsRepository.findByTransactionId(transactionId);
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

	public TransactionDetails getPaymentReceipt(String email) {
		TrafficViolator trafficViolator = trafficViolatorRepository.getTrafficViolatorByEmail(email);
		return transactionDetailsRepository.getByTrafficViolator(trafficViolator.getEmail());
	}
}
