package com.supinfo.notetonsta.android.listener;

import com.supinfo.notetonsta.android.ViewInterventionActivity;
import com.supinfo.notetonsta.android.entity.Campus;
import com.supinfo.notetonsta.android.entity.ComplexIntervention;
import com.supinfo.notetonsta.android.entity.SimpleIntervention;
import com.supinfo.notetonsta.android.handler.GetInterventionHandler;
import com.supinfo.notetonsta.android.resource.GetInterventionResource;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class ChooseInterventionListener implements OnClickListener {
	private static Activity activity;
	
	private SimpleIntervention sIntervention;
	private Campus campus;
	
	public static void setActivity(Activity a) {
		activity = a;
	}
	
	public ChooseInterventionListener(SimpleIntervention i, Campus c) {
		sIntervention = i;
		campus = c;
	}

	public void onClick(View arg0) {
		ComplexIntervention cIntervention = new ComplexIntervention();
		
		Intent intent = new Intent(activity, ViewInterventionActivity.class);
		intent.putExtra("intervention", cIntervention);
		intent.putExtra("campus", campus);
		
		GetInterventionHandler handler = new GetInterventionHandler(activity,  intent);
		GetInterventionResource resource = new GetInterventionResource(handler, cIntervention, sIntervention.getId());
		Thread thread = new Thread(resource);
		thread.start();
	}

}
