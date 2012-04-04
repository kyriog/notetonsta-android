package com.supinfo.notetonsta.android.handler;

import com.supinfo.notetonsta.android.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Message;
import android.widget.Toast;

public class GetInterverventionsHandler extends BaseHandler {
	private Activity activity;
	private Resources resources;
	private Intent intent;
	private ProgressDialog progress;
	
	public GetInterverventionsHandler(Activity a, Resources r, Intent i) {
		activity = a;
		resources = r;
		intent = i;
	}

	@Override
	public void handleMessage(Message msg) {
		Toast toast = null;
		switch(msg.arg1) {
		case STATUS_STARTED:
			progress = new ProgressDialog(activity);
			progress.setIndeterminate(true);
			progress.setMessage(resources.getString(R.string.interventions_load));
			progress.setCancelable(false);
			progress.show();
			break;
		case STATUS_FINISHED_OK:
			progress.dismiss();
			activity.startActivity(intent);
			break;
		case STATUS_FINISHED_NORESULT:
			toast = Toast.makeText(activity, R.string.interventions_error_noresult, 1000);
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
