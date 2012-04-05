package com.supinfo.notetonsta.android;

import com.supinfo.notetonsta.android.entity.Campus;
import com.supinfo.notetonsta.android.entity.ComplexIntervention;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewInterventionActivity extends Activity {
	ComplexIntervention intervention;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewintervention);
		
		Bundle extras = getIntent().getExtras();
		Campus campus = (Campus) extras.getSerializable("campus");
		intervention = (ComplexIntervention) extras.getSerializable("intervention");
		
		LinearLayout interventionMainLayout = (LinearLayout) findViewById(R.id.intervention_main_layout);
		LinearLayout interventionNoteLayout = (LinearLayout) findViewById(R.id.intervention_note_layout);
		TextView interventionCampus = (TextView) findViewById(R.id.intervention_campus);
		TextView interventionName = (TextView) findViewById(R.id.intervention_name);
		TextView interventionDescription = (TextView) findViewById(R.id.intervention_description);
		TextView interventionDateStart = (TextView) findViewById(R.id.intervention_date_start);
		TextView interventionDateEnd = (TextView) findViewById(R.id.intervention_date_end);
		TextView interventionEvaluationNumber = (TextView) findViewById(R.id.intervention_evalution_number);
		TextView interventionSpeakerNote = (TextView) findViewById(R.id.intervention_speaker_note);
		TextView interventionSlideNote = (TextView) findViewById(R.id.intervention_slide_note);
		TextView interventionGlobalNote = (TextView) findViewById(R.id.intervention_global_note);
		
		interventionCampus.setText(campus.getName());
		interventionName.setText(intervention.getName());
		interventionDescription.setText(intervention.getDescription());
		interventionDateStart.setText(intervention.getDateStart());
		interventionDateEnd.setText(intervention.getDateEnd());
		interventionEvaluationNumber.setText(intervention.getEvaluationNumber().toString());
		if(intervention.getEvaluationNumber() > 0) {
			interventionSpeakerNote.setText(intervention.getSpeakerNote().toString());
			interventionSlideNote.setText(intervention.getSlideNote().toString());
			interventionGlobalNote.setText(intervention.getGlobalNote().toString());
		} else
			interventionMainLayout.removeView(interventionNoteLayout);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.viewintervention, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.menuitem_evaluate_intervention:
			Intent intent = new Intent(this, EvaluateInterventionActivity.class);
			intent.putExtra("intervention", intervention);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
}
