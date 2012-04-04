package com.supinfo.notetonsta.android.handler;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Message;
import android.widget.Toast;

import com.supinfo.notetonsta.android.R;

public class GetInterventionHandler extends BaseHandler {
	private static Resources resources;
	
	private Activity activity;
	private Intent intent;
	private ProgressDialog progress;
	
	public static void setResources(Resources r) {
		resources = r;
	}
	
	public GetInterventionHandler(Activity a, Intent i) {
		activity = a;
		intent = i;
	}

	@Override
	public void handleMessage(Message msg) {
		Toast toast = null;
		switch(msg.arg1) {
		case STATUS_STARTED:
			progress = new ProgressDialog(activity);
			progress.setIndeterminate(true);
			progress.setMessage(resources.getString(R.string.intervention_load));
			progress.setCancelable(false);
			progress.show();
			break;
		case STATUS_FINISHED_OK:
			progress.dismiss();
			activity.startActivity(intent);
			break;
		case STATUS_ERROR_TIMEDOUT:
			toast = Toast.makeText(activity, R.string.error_timedout, 1000);
			break;
		case STATUS_ERROR_CONNREFUSED:
			toast = Toast.makeText(activity, R.string.error_connrefused, 1000);
			break;
		case STATUS_ERROR_JSON:
			toast = Toast.makeText(activity, R.string.error_json, 1000);
			break;
		case STATUS_ERROR_UNKNOWN:
			toast = Toast.makeText(activity, R.string.error_unknown, 1000);
		}
		if(toast != null) {
			progress.dismiss();
			toast.show();
		}
		super.handleMessage(msg);
	}
}
