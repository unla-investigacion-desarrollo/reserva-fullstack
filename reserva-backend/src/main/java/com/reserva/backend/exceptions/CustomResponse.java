package com.reserva.backend.exceptions;

public class CustomResponse<T> {
	
	private T data;
	private ErrorDetails error;
	
	public CustomResponse(T data, ErrorDetails error) {
		super();
		this.data = data;
		this.error = error;
	}

	public CustomResponse() {
		super();
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public ErrorDetails getError() {
		return error;
	}

	public void setError(ErrorDetails error) {
		this.error = error;
	}

}
