package io.gustavo.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Log {

	@NotNull(message = "Status can not be null")
	private Status status;
	@NotBlank(message = "Message can not be blank")
	private String message;
	
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	 
	@Override
	public String toString() {
		return "[Log: " + this.status + " - " + this.message;
	}
	
}
