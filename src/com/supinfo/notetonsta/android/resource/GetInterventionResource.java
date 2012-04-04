package com.supinfo.notetonsta.android.resource;

import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
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
import com.supinfo.notetonsta.android.handler.BaseHandler;

public class GetInterventionResource implements Runnable {
	private String jsonString;
	
	private Handler handler;
	private ComplexIntervention intervention;
	private Long idIntervention;
	
	public GetInterventionResource(Handler h, ComplexIntervention i, Long idIntervention) {
		handler = h;
		intervention = i;
		this.idIntervention = idIntervention;
	}

	public void run() {
		Message message = handler.obtainMessage();
		message.arg1 = BaseHandler.STATUS_STARTED;
		handler.sendMessage(message);
		message = handler.obtainMessage();
		try {
			getInterventionRaw();
			parseJSON();
			message.arg1 = BaseHandler.STATUS_FINISHED_OK;
		} catch(ConnectTimeoutException e) { 
			Log.w("Warning",e.getMessage(),e);
			message.arg1 = BaseHandler.STATUS_ERROR_TIMEDOUT;
		} catch(HttpHostConnectException e) {
			Log.w("Warning",e.getMessage(),e);
			message.arg1 = BaseHandler.STATUS_ERROR_CONNREFUSED;
		} catch (JSONException e) {
			// This will be caught if JSON library can't parse server return
			Log.w("Warning",e.getMessage(),e);
			message.arg1 = BaseHandler.STATUS_ERROR_JSON;
		} catch(Exception e) {
			// We've to catch any unknown exception
			Log.e("Error",e.getMessage(),e);
			message.arg1 = BaseHandler.STATUS_ERROR_UNKNOWN;
		}  finally {
			handler.sendMessage(message);
		}
	}

	private void getInterventionRaw() throws ConnectTimeoutException, HttpHostConnectException, Exception {
		jsonString = null;
		BasicHttpParams basicHttpParams = new BasicHttpParams();
		// Connection must time out after 10 second to prevent infinite loop
		HttpConnectionParams.setConnectionTimeout(basicHttpParams, 10000);
		HttpClient httpClient = new DefaultHttpClient(basicHttpParams);
		
		HttpGet httpGet = new HttpGet();
		httpGet.setHeader("Accept", "application/json");
		URI uri = new URI("http://192.168.0.13:8080/Note_ton_STA/resource/intervention/"+idIntervention);
		httpGet.setURI(uri);
		
		HttpResponse response = httpClient.execute(httpGet);
		jsonString = EntityUtils.toString(response.getEntity()); 
	}
	
	private void parseJSON() throws JSONException {
		JSONObject json = new JSONObject(jsonString);
		
		intervention.setId(json.getLong("id"));
		intervention.setName(json.getString("name"));
		intervention.setDescription(json.getString("description"));
		intervention.setDateStart(json.getString("dateStart"));
		intervention.setDateEnd(json.getString("dateEnd"));
		intervention.setStatus(json.getInt("status"));
		intervention.setEvaluationNumber(json.getInt("evaluationNumber"));
		intervention.setSpeakerNote(json.getDouble("speakerNote"));
		intervention.setSlideNote(json.getDouble("slideNote"));
		intervention.setGlobalNote(json.getDouble("globalNote"));
	}
}
