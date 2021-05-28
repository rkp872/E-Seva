package com.rohit.dto;

public class EmailRequest {
	private String to;
	private String subject;
	private String message;

	public EmailRequest() {
		super();
	}

	public EmailRequest(String from, String subject, String message) {
		super();
		this.to = from;
		this.subject = subject;
		this.message = message;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String from) {
		this.to = from;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "EmailRequest [to=" + to + ", subject=" + subject + ", message=" + message + "]";
	}

}
