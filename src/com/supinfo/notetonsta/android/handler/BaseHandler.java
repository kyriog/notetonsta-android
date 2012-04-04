package com.supinfo.notetonsta.android.handler;

import android.os.Handler;

public abstract class BaseHandler extends Handler {
	public static final int STATUS_STARTED = 1;
	public static final int STATUS_FINISHED_OK = 2;
	public static final int STATUS_FINISHED_NORESULT = 7;
	public static final int STATUS_ERROR_TIMEDOUT = 3;
	public static final int STATUS_ERROR_CONNREFUSED = 4;
	public static final int STATUS_ERROR_JSON = 5;
	public static final int STATUS_ERROR_UNKNOWN = 6;
}
