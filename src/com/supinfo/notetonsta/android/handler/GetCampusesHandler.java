package com.supinfo.notetonsta.android.handler;

import com.supinfo.notetonsta.android.R;
import com.supinfo.notetonsta.android.entity.Campus;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class GetCampusesHandler extends Handler {
	public static final int STATUS_STARTED = 1;
	public static final int STATUS_FINISHED_OK = 2;
	public static final int STATUS_ERROR_TIMEDOUT = 3;
	public static final int STATUS_ERROR_CONNREFUSED = 4;
	public static final int STATUS_ERROR_JSON = 5;
	public static final int STATUS_ERROR_UNKNOWN = 6;
	
	private Context context;
	private Resources resources;
	private ArrayAdapter<Campus> adapter;
	private ProgressDialog progress;
	
	public GetCampusesHandler(Context c, Resources r, ArrayAdapter<Campus> a) {
		context = c;
		resources = r;
		adapter = a;
	}

	@Override
	public void handleMessage(Message msg) {
		Toast toast = null;
		switch(msg.arg1) {
		case STATUS_STARTED:
			progress = new ProgressDialog(context);
			progress.setIndeterminate(true);
			progress.setMessage(resources.getString(R.string.campus_refresh));
			progress.setCancelable(false);
			progress.show();
			break;
		case STATUS_FINISHED_OK:
			adapter.notifyDataSetChanged();
			toast = Toast.makeText(context, R.string.campus_refresh_finished, 3000);
			break;
		case STATUS_ERROR_TIMEDOUT:
			toast = Toast.makeText(context, R.string.campus_refresh_error_timedout, 1000);
			break;
		case STATUS_ERROR_CONNREFUSED:
			toast = Toast.makeText(context, R.string.campus_refresh_error_connrefused, 1000);
			break;
		case STATUS_ERROR_JSON:
			toast = Toast.makeText(context, R.string.campus_refresh_error_json, 1000);
			break;
		case STATUS_ERROR_UNKNOWN:
			toast = Toast.makeText(context, R.string.campus_refresh_error_unknown, 1000);
		}
		if(toast != null) {
			progress.dismiss();
			toast.show();
		}
		super.handleMessage(msg);
	}

}
