package com.hit.exception;

 
public class UnknownIdException extends Exception  {


	public UnknownIdException(String message, Throwable err) {
		super(message, err);
	}
	
	public UnknownIdException(Throwable err) {
		super(err);
	}

}
