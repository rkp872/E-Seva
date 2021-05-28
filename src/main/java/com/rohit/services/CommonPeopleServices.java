package com.rohit.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.rohit.model.TrafficViolator;
import com.rohit.model.TransactionDetails;
import com.rohit.model.User;
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
			transactionDetails.setTransactionId(hashInp.hashCode());

			transactionDetailsRepository.save(transactionDetails);

			return true;

		} catch (Exception e) {
			return false;
		}

	}
}
