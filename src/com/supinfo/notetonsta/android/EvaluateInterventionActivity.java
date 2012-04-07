package com.supinfo.notetonsta.android;

import com.supinfo.notetonsta.android.entity.ComplexIntervention;
import com.supinfo.notetonsta.android.entity.Evaluation;
import com.supinfo.notetonsta.android.exception.NoIdboosterException;
import com.supinfo.notetonsta.android.handler.AddEvaluationHandler;
import com.supinfo.notetonsta.android.resource.AddEvaluationResource;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class EvaluateInterventionActivity extends Activity implements View.OnClickListener {
	private Evaluation evaluation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.evaluateintervention);
		
		Button evaluationSubmit = (Button) findViewById(R.id.evaluation_submit);
		
		evaluationSubmit.setOnClickListener(this);
	}

	public void onClick(View view) {
		switch(view.getId()) {
		case R.id.evaluation_submit:
			Bundle extras = getIntent().getExtras();
			ComplexIntervention intervention = (ComplexIntervention) extras.getSerializable("intervention");
			getIntent().putExtra("intervention", intervention);
			
			Toast toast = null;
			try {
				generateEvaluationFromView();
			} catch (NoIdboosterException e) {
				Log.i("info", e.getMessage(), e);
				toast = Toast.makeText(this, R.string.evaluation_error_noidbooster, 3000);
			} catch (NumberFormatException e) {
				Log.w("warning", e.getMessage(), e);
				toast = Toast.makeText(this, R.string.evaluation_error_wrongidbooster, 3000);
			}
			if(toast != null) {
				toast.show();
				return;
			}
			
			AddEvaluationHandler handler = new AddEvaluationHandler(this);
	        AddEvaluationResource resource = new AddEvaluationResource(handler, intervention, evaluation);
	        Thread thread = new Thread(resource);
	        thread.start();
		}
	}
	
	private void generateEvaluationFromView() throws NoIdboosterException, NumberFormatException {
		EditText evaluationIdbooster = (EditText) findViewById(R.id.evaluation_idbooster);
		RatingBar evaluationSpeakerKnowledge = (RatingBar) findViewById(R.id.evaluation_speaker_knowledge);
		RatingBar evaluationSpeakerCapacity = (RatingBar) findViewById(R.id.evaluation_speaker_capacity);
		RatingBar evaluationSpeakerQuality = (RatingBar) findViewById(R.id.evaluation_speaker_quality);
		RatingBar evaluationSlideContent = (RatingBar) findViewById(R.id.evaluation_slide_content);
		RatingBar evaluationSlideFormat = (RatingBar) findViewById(R.id.evaluation_slide_format);
		RatingBar evaluationSlideExamples = (RatingBar) findViewById(R.id.evaluation_slide_examples);
		EditText evaluationComment = (EditText) findViewById(R.id.evaluation_comment);
		
		evaluation = new Evaluation();
		if("".equals(evaluationIdbooster.getText().toString()))
			throw new NoIdboosterException();
		evaluation.setIdBooster(Integer.parseInt(evaluationIdbooster.getText().toString()));
		evaluation.setSpeakerKnowledge((int) evaluationSpeakerKnowledge.getRating());
		evaluation.setSpeakerAbility((int) evaluationSpeakerCapacity.getRating());
		evaluation.setSpeakerAnswers((int) evaluationSpeakerQuality.getRating());
		evaluation.setSlideContent((int) evaluationSlideContent.getRating());
		evaluation.setSlideFormat((int) evaluationSlideFormat.getRating());
		evaluation.setSlideExamples((int) evaluationSlideExamples.getRating());
		evaluation.setComment(evaluationComment.getText().toString());
	}

}
