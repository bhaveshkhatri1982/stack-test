package com.stack.utility;

public class Status
{
	private boolean success	=	false;
	private String message	=	"";
	
	public Status(boolean success, String message)
	{
		this.success = success;
		this.message = message;
	}
	
	// getters and setters
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
