package com.reserva.backend.exceptions;

import java.util.Date;

public class ErrorDetails {

	private Date time;
	private String result;
	private String from;

	public ErrorDetails(Date time, String result, String from) {
		super();
		this.time = time;
		this.result = result;
		this.from = from;
	}

	public ErrorDetails() {
		super();
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

}