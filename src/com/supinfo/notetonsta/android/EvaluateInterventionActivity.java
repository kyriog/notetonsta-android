package com.supinfo.notetonsta.android;

import com.supinfo.notetonsta.android.entity.ComplexIntervention;

import android.app.Activity;
import android.os.Bundle;

public class EvaluateInterventionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.evaluateintervention);
		
		Bundle extras = getIntent().getExtras();
		ComplexIntervention intervention = (ComplexIntervention) extras.getSerializable("intervention");
	}

}
