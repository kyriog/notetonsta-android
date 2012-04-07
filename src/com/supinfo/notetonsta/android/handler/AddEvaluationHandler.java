package com.supinfo.notetonsta.android.handler;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Message;
import android.widget.Toast;

import com.supinfo.notetonsta.android.R;

public class AddEvaluationHandler extends BaseHandler {
	private Activity activity;
	private ProgressDialog progress;
	
	public AddEvaluationHandler(Activity a) {
		activity = a;
	}

	@Override
	public void handleMessage(Message msg) {
		Toast toast = null;
		switch(msg.arg1) {
		case STATUS_STARTED:
			progress = new ProgressDialog(activity);
			progress.setIndeterminate(true);
			progress.setMessage(activity.getResources().getString(R.string.evaluation_sending));
			progress.setCancelable(false);
			progress.show();
			break;
		case STATUS_FINISHED_OK:
			activity.setResult(Activity.RESULT_OK, activity.getIntent());
			activity.finish();
			toast = Toast.makeText(activity, R.string.evaluation_sent, 3000);
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
