package com.stack.operations.model;

public class PeekStatus
{
	private int peekedNumber	=	0;
	private String message		=	"";
	
	public PeekStatus(int peekedNumber, String message)
	{
		this.peekedNumber = peekedNumber;
		this.message = message;
	}
	
	// getters and setters
	public int getPeekedNumber() {
		return peekedNumber;
	}

	public void setPeekedNumber(int peekedNumber) {
		this.peekedNumber = peekedNumber;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
