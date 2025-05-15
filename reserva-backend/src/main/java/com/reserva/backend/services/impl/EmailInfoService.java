package com.reserva.backend.services.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.reserva.backend.dto.email.EmailInfoDto;
import com.reserva.backend.services.IEmailInfoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EmailInfoService implements IEmailInfoService {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	private final List<EmailInfoDto> lst = new ArrayList<>();

	@Override
	public void send(EmailInfoDto email) throws MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setTo(email.getTo());
		helper.setSubject(email.getSubject());
		helper.setText(email.getBody(), true);
		helper.setReplyTo(email.getFrom());
		String[] cc = email.getCc();
		if (cc != null && cc.length > 0) {
			helper.setCc(cc);
		}
		String[] bcc = email.getBcc();
		if (bcc != null && bcc.length > 0) {
			helper.setBcc(bcc);
		}
		String[] attachments = email.getAttachments();
		if (attachments != null && attachments.length > 0) {
			for (String path : attachments) {
				File file = new File(path);
				helper.addAttachment(file.getName(), file);
			}
		}
		javaMailSender.send(message);
	}

	@Override
	public void send(String to, String subject, String body) throws MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(body, true);
		javaMailSender.send(message);
	}

	@Override
	public void queue(EmailInfoDto email) {
		lst.add(email);
	}

	@Override
	public void queue(String to, String subject, String body) {
		queue(new EmailInfoDto(to, subject, body));
	}

	@Override
	@Scheduled(fixedDelay = 5000)
	public void run() {
		while (!lst.isEmpty()) {
			EmailInfoDto mail = lst.remove(0);
			try {
				this.send(mail);
			} catch (MessagingException e) {
				log.error(e.getMessage());
			}
		}
	}

}
