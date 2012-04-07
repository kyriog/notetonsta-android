package com.supinfo.notetonsta.android.resource;

import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.supinfo.notetonsta.android.entity.ComplexIntervention;
import com.supinfo.notetonsta.android.entity.Evaluation;
import com.supinfo.notetonsta.android.handler.BaseHandler;

public class AddEvaluationResource extends BaseResource implements Runnable {
	private JSONObject generatedJSON;
	private String jsonString;
	
	private Handler handler;
	private ComplexIntervention intervention;
	private Evaluation evaluation;
	
	public AddEvaluationResource(Handler h, ComplexIntervention i, Evaluation e) {
		handler = h;
		intervention = i;
		evaluation = e;
	}

	public void run() {
		Message message = handler.obtainMessage();
		message.arg1 = BaseHandler.STATUS_STARTED;
		handler.sendMessage(message);
		message = handler.obtainMessage();
		try {
			generateJSON();
			addEvaluationRaw();
			updateIntervention();
			message.arg1 = BaseHandler.STATUS_FINISHED_OK;
		} catch (ConnectTimeoutException e) {
			Log.w("Warning",e.getMessage(),e);
			message.arg1 = BaseHandler.STATUS_ERROR_TIMEDOUT;
		} catch (HttpHostConnectException e) {
			Log.w("Warning",e.getMessage(),e);
			message.arg1 = BaseHandler.STATUS_ERROR_CONNREFUSED;
		} catch (JSONException e) {
			// This will be caught if JSON library can't parse server return
			Log.w("Warning",e.getMessage(),e);
			message.arg1 = BaseHandler.STATUS_ERROR_JSON;
		} catch (Exception e) {
			// We've to catch any unknown exception
			Log.e("Error",e.getMessage(),e);
			message.arg1 = BaseHandler.STATUS_ERROR_UNKNOWN;
		} finally {
			handler.sendMessage(message);
		}
	}
	
	private void generateJSON() throws JSONException {
		generatedJSON = new JSONObject();
		
		generatedJSON.put("idIntervention", intervention.getId());
		generatedJSON.put("idBooster", evaluation.getIdBooster());
		generatedJSON.put("speakerKnowledge", evaluation.getSpeakerKnowledge());
		generatedJSON.put("speakerAbility", evaluation.getSpeakerAbility());
		generatedJSON.put("speakerAnswers", evaluation.getSpeakerAnswers());
		generatedJSON.put("slideContent", evaluation.getSlideContent());
		generatedJSON.put("slideFormat", evaluation.getSlideFormat());
		generatedJSON.put("slideExamples", evaluation.getSlideExamples());
		generatedJSON.put("comment", evaluation.getComment());
	}
	
	private void addEvaluationRaw() throws ConnectTimeoutException, HttpHostConnectException, Exception {
		jsonString = null;
		BasicHttpParams basicHttpParams = new BasicHttpParams();
		// Connection must time out after 10 second to prevent infinite loop
		HttpConnectionParams.setConnectionTimeout(basicHttpParams, 10000);
		HttpClient httpClient = new DefaultHttpClient(basicHttpParams);
		
		HttpPost httpPost = new HttpPost();
		httpPost.setHeader("Accept", "application/json");
		URI uri = new URI(baseURI+"evaluation");
		httpPost.setURI(uri);
		httpPost.setEntity(new StringEntity(generatedJSON.toString()));
		httpPost.setHeader("Content-Type", "application/json");
		
		HttpResponse response = httpClient.execute(httpPost);
		jsonString = EntityUtils.toString(response.getEntity()); 
	}

	private void updateIntervention() throws JSONException {
		JSONObject json = new JSONObject(jsonString);
		
		intervention.setEvaluationNumber(json.getInt("evaluationNumber"));
		intervention.setSpeakerNote(json.getDouble("speakerNote"));
		intervention.setSlideNote(json.getDouble("slideNote"));
		intervention.setGlobalNote(json.getDouble("globalNote"));
	}
}
