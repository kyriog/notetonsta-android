package com.supinfo.notetonsta.android.exception;

public class NoIdboosterException extends Throwable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7039402853701258114L;

	@Override
	public String getMessage() {
		return "No idbooster has been set";
	}
}
