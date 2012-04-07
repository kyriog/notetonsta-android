package com.supinfo.notetonsta.android;

import java.util.ArrayList;

import com.supinfo.notetonsta.android.entity.Campus;
import com.supinfo.notetonsta.android.entity.SimpleIntervention;
import com.supinfo.notetonsta.android.handler.GetCampusesHandler;
import com.supinfo.notetonsta.android.handler.GetInterventionsHandler;
import com.supinfo.notetonsta.android.resource.BaseResource;
import com.supinfo.notetonsta.android.resource.GetCampusesResource;
import com.supinfo.notetonsta.android.resource.GetInterventionsResource;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class ChooseCampusActivity extends Activity implements View.OnClickListener, OnClickListener {
	private SharedPreferences preferences;
	private EditText seturiEdittext;
	private Spinner campusSpinner;
	private ArrayAdapter<Campus> campusAdapter;
	private ArrayList<Campus> campusList;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosecampus);
        
        preferences = getPreferences(Context.MODE_PRIVATE);
        BaseResource.setBaseURI(preferences.getString("baseURI", ""));
        
        campusSpinner = (Spinner) findViewById(R.id.campuslist);
        Button campusButton = (Button) findViewById(R.id.campusbutton);
        
        campusButton.setOnClickListener(this);
        
        campusList = new ArrayList<Campus>();
        campusAdapter = new ArrayAdapter<Campus>(this, android.R.layout.simple_spinner_item, campusList);
        
        campusSpinner.setAdapter(campusAdapter);
        
        campusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        GetCampusesHandler handler = new GetCampusesHandler(this, getResources(), campusAdapter);
        GetCampusesResource resource = new GetCampusesResource(handler, campusList);
        Thread thread = new Thread(resource);
        thread.start();
    }

	public void onClick(View v) {
		ArrayList<SimpleIntervention> interventions = new ArrayList<SimpleIntervention>();
		Campus campus = (Campus) campusSpinner.getSelectedItem();
		
		Intent intent = new Intent(this, ChooseInterventionActivity.class);
		intent.putExtra("campus", campus);
		intent.putExtra("interventions", interventions);
		
		GetInterventionsHandler handler = new GetInterventionsHandler(this, getResources(), intent);
		GetInterventionsResource resource = new GetInterventionsResource(handler, interventions, campus.getId());
		Thread thread = new Thread(resource);
		thread.start();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.choosecampus, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.menuitem_seturi:
			seturiEdittext = new EditText(this);
			seturiEdittext.setText(preferences.getString("baseURI", "http://"));
			
			AlertDialog seturiDialog = new AlertDialog.Builder(this).create();
			seturiDialog.setTitle(R.string.menuitem_seturi);
			seturiDialog.setView(seturiEdittext);
			seturiDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getResources().getText(android.R.string.cancel), this);
			seturiDialog.setButton(AlertDialog.BUTTON_POSITIVE, getResources().getText(android.R.string.ok), this);
			seturiDialog.show();
			break;
		case R.id.menuitem_refresh:
			GetCampusesHandler handler = new GetCampusesHandler(this, getResources(), campusAdapter);
	        GetCampusesResource resource = new GetCampusesResource(handler, campusList);
	        Thread thread = new Thread(resource);
	        thread.start();
	        break;
		case R.id.menuitem_exit:
			finish();
		}
		return super.onOptionsItemSelected(item);
	}

	public void onClick(DialogInterface arg0, int button) {
		if(button == AlertDialog.BUTTON_POSITIVE) {
			String newURI = seturiEdittext.getText().toString();
			if(!"/".equals(newURI.substring(newURI.length()-1)))
				newURI += "/";
			SharedPreferences.Editor preferencesEditor = preferences.edit();
			preferencesEditor.putString("baseURI", newURI);
			preferencesEditor.commit();
			BaseResource.setBaseURI(newURI);
		}
	}
}