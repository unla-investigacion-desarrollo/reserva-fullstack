package com.reserva.backend.entities;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class TokenVerification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String token;
	private Date createdAt;
	private Date expiratedAt;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	public TokenVerification(User user) {
		super();
		this.token = generateToken();
		this.createdAt = new Date();
		this.expiratedAt = getEpirationTime(10);
		this.user = user;
	}
	
	public Date getEpirationTime(int expiration) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Timestamp(calendar.getTime().getTime()));
		calendar.add(Calendar.MINUTE, expiration);
		return new Date(calendar.getTime().getTime());
	}
	
	public String generateToken() {
		return UUID.randomUUID().toString();
	}
	
}
