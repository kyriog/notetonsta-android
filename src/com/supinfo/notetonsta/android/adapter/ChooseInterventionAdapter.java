package com.supinfo.notetonsta.android.adapter;

import java.util.List;

import com.supinfo.notetonsta.android.R;
import com.supinfo.notetonsta.android.entity.SimpleIntervention;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChooseInterventionAdapter extends BaseAdapter {
	private static Resources resources;
	
	private Context context;
	private List<SimpleIntervention> interventions;
	
	public static void setResources(Resources r) {
		resources = r;
	}
	
	public ChooseInterventionAdapter(Context c, List<SimpleIntervention> i) {
		context = c;
		interventions = i;
	}

	public int getCount() {
		return interventions.size();
	}

	public SimpleIntervention getItem(int position) {
		return interventions.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		TextView title = new TextView(context);
		TextView subtitle = new TextView(context);
		LinearLayout layout = new LinearLayout(context);
		SimpleIntervention intervention = getItem(position);
		
		title.setTextSize(25);
		title.setText(intervention.getName());
		
		subtitle.setText(generateSubtitle(intervention));
		
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.addView(title);
		layout.addView(subtitle);
		
		if(position%2 == 0)
			layout.setBackgroundColor(Color.DKGRAY);
		
		return layout;
	}
	
	private String generateSubtitle(SimpleIntervention i) {
		String subtitle = "";
		switch(i.getStatus()) {
		case 0:
			subtitle += resources.getString(R.string.intervention_state_started);
			break;
		case 1:
			subtitle += resources.getString(R.string.intervention_state_inprogress);
			break;
		case 2:
			subtitle += resources.getString(R.string.intervention_state_finished);
		}
		subtitle += " / ";
		subtitle += i.getDateStart();
		subtitle += " -> ";
		subtitle += i.getDateEnd();
		
		return subtitle;
	}

}
