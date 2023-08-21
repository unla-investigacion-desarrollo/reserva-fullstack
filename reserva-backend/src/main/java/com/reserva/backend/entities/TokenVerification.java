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

	public TokenVerification() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getExpiratedAt() {
		return expiratedAt;
	}

	public void setExpiratedAt(Date expiratedAt) {
		this.expiratedAt = expiratedAt;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
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
