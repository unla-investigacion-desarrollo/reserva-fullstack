package com.reserva.backend.services;

import javax.mail.MessagingException;

import com.reserva.backend.dto.email.EmailInfoDto;

public interface IEmailInfoService {
	
	public void send(EmailInfoDto email) throws MessagingException;

	public void send(String to, String subject, String body) throws MessagingException;

	public void queue(EmailInfoDto email);

	public void queue(String to, String subject, String body);

	public void run();

}
