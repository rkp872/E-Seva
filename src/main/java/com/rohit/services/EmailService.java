package com.rohit.services;

import java.util.Properties;
import java.util.concurrent.Future;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	// @Autowired
	// private Environment env;

	@Value("${spring.email.username}")
	private String email;

	@Value("${spring.email.password}")
	private String password;

	@Async
	public Future<String> sendEmail(String subject, String message, String to) {

		System.out.println("Inside mail Service");

		// String email = env.getProperty("spring.email.username");
		// String password = env.getProperty("spring.email.password");

		System.out.println(email);
		System.out.println(password);

		String from = email;

		// VARIABLE FOR GMAIL HOST
		String host = "smtp.gmail.com";

		// GET THE SYSTEM PROPERTIES

		Properties properties = System.getProperties();
		System.out.println("Properties : " + properties);

		// SETTING IMPORTANT INFORMATION TO PROPERTIES OBJECT

		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");

		// STEP 1 : GET SESSION OBJECT
		// -----------------------------------------
		Session session = Session.getInstance(properties, new Authenticator() {

			@Override
			protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
				return new javax.mail.PasswordAuthentication(email, password);
			}

		});
		session.setDebug(true);

		// STEP 2 : COMPOSE THE MESSAGE
		// -----------------------------------------------
		MimeMessage mimeMessage = new MimeMessage(session);
		try {
			mimeMessage.setFrom(from);
			mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
			mimeMessage.setSubject(subject);
			mimeMessage.setText(message);

			// STEP : 3 SEND THE MEAASGE USING TRANSPORT CLASS
			Transport.send(mimeMessage);
			System.out.println("Sent Successfully....");

		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return null;

	}

}
