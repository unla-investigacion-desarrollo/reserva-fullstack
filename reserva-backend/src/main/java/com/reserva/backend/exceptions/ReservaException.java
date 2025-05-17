package com.reserva.backend.exceptions;

import org.springframework.http.HttpStatus;

//Esta clase la voy a usar para excepcioens mas personalizadas agregando el estado
public class ReservaException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String message;
	private HttpStatus status;

	public ReservaException(String message, HttpStatus status) {
		super();
		this.message = message;
		this.status = status;
	}

	public ReservaException(String message, String message2, HttpStatus status) {
		super();
		this.message = message;
		this.message = message2;
		this.status = status;
	}

	public ReservaException() {
		super();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

}
