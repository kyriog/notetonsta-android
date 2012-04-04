package com.supinfo.notetonsta.android;

import java.util.ArrayList;

import com.supinfo.notetonsta.android.entity.Campus;
import com.supinfo.notetonsta.android.entity.SimpleIntervention;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ChooseInterventionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chooseintervention);
		
		Bundle extras = getIntent().getExtras();
		Campus campus = (Campus) extras.getSerializable("campus");
		@SuppressWarnings("unchecked")
		ArrayList<SimpleIntervention> interventions = (ArrayList<SimpleIntervention>) extras.getSerializable("interventions");
		
		TextView interventionsCampus = (TextView) findViewById(R.id.interventions_campus);
		ListView interventionsList = (ListView) findViewById(R.id.interventions_list);
		
		interventionsCampus.setText(campus.getName());
		ArrayAdapter<SimpleIntervention> adapter = new ArrayAdapter<SimpleIntervention>(this, android.R.layout.simple_list_item_1, interventions);
		interventionsList.setAdapter(adapter);
	}

}
