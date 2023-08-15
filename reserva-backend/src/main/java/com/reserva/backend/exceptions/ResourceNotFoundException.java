package com.reserva.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String name;
	private String text;
	private long value;

	public ResourceNotFoundException(String name, String text, long value) {
		super(String.format("Not found %s -> %s -> id: %s",name, text, value));
		this.name = name;
		this.text = text;
		this.value = value;
	}

	public ResourceNotFoundException() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}

}
