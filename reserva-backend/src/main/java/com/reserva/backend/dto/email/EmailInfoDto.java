package com.reserva.backend.dto.email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailInfoDto {
	
	private String from;
	private String to;
	private String[] cc;
	private String[] bcc;
	private String subject;
	private String body;
	private String[] attachments;

	public EmailInfoDto(String to, String subject, String body) {
		this.to = to;
		this.subject = subject;
		this.body = body;
	}

}
