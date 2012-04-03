package com.supinfo.notetonsta.android;

import com.supinfo.notetonsta.android.entity.Campus;
import com.supinfo.notetonsta.android.handler.GetCampusesHandler;
import com.supinfo.notetonsta.android.resource.GetCampusesResource;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ChooseCampusActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosecampus);
        
        Spinner campusList = (Spinner) findViewById(R.id.campuslist);
        
        ArrayAdapter<Campus> campusAdapter = new ArrayAdapter<Campus>(this, android.R.layout.simple_spinner_item);
        
        campusList.setAdapter(campusAdapter);
        
        campusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        GetCampusesHandler handler = new GetCampusesHandler(this, getResources(), campusAdapter);
        GetCampusesResource resource = new GetCampusesResource(handler, campusAdapter);
        Thread thread = new Thread(resource);
        thread.start();
    }
}