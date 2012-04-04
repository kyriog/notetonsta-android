package com.supinfo.notetonsta.android.exception;

public class ZeroResultException extends Throwable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8252964109361919212L;

	@Override
	public String getMessage() {
		return "No result returns";
	}
}
