package com.supinfo.notetonsta.android.resource;

import java.net.URI;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.supinfo.notetonsta.android.entity.SimpleIntervention;
import com.supinfo.notetonsta.android.exception.ZeroResultException;
import com.supinfo.notetonsta.android.handler.BaseHandler;

public class GetInterventionsResource extends BaseResource implements Runnable {
	private String jsonString;
	
	private Handler handler;
	private ArrayList<SimpleIntervention> list;
	private Long idCampus;
	
	public GetInterventionsResource(Handler h, ArrayList<SimpleIntervention> l, Long idCampus) {
		handler = h;
		list = l;
		this.idCampus = idCampus;
	}

	public void run() {
		Message message = handler.obtainMessage();
		message.arg1 = BaseHandler.STATUS_STARTED;
		handler.sendMessage(message);
		message = handler.obtainMessage();
		try {
			getInterventionsRaw();
			parseJSON();
			message.arg1 = BaseHandler.STATUS_FINISHED_OK;
		} catch(ConnectTimeoutException e) { 
			Log.w("Warning",e.getMessage(),e);
			message.arg1 = BaseHandler.STATUS_ERROR_TIMEDOUT;
		} catch(HttpHostConnectException e) {
			Log.w("Warning",e.getMessage(),e);
			message.arg1 = BaseHandler.STATUS_ERROR_CONNREFUSED;
		} catch (ZeroResultException e) {
			Log.i("Info",e.getMessage(),e);
			message.arg1 = BaseHandler.STATUS_FINISHED_NORESULT;
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

	private void getInterventionsRaw() throws ConnectTimeoutException, HttpHostConnectException, Exception {
		jsonString = null;
		BasicHttpParams basicHttpParams = new BasicHttpParams();
		// Connection must time out after 10 second to prevent infinite loop
		HttpConnectionParams.setConnectionTimeout(basicHttpParams, 10000);
		HttpClient httpClient = new DefaultHttpClient(basicHttpParams);
		
		HttpGet httpGet = new HttpGet();
		httpGet.setHeader("Accept", "application/json");
		URI uri = new URI(baseURI+"campus/"+idCampus);
		httpGet.setURI(uri);
		
		HttpResponse response = httpClient.execute(httpGet);
		jsonString = EntityUtils.toString(response.getEntity()); 
	}
	
	private void parseJSON() throws JSONException, ZeroResultException {
		if("null".equals(jsonString)) {
			throw new ZeroResultException();
		}
		JSONObject jsonObj = new JSONObject(jsonString);
		JSONArray json = jsonObj.optJSONArray("intervention");
		if(json == null) {
			json = new JSONArray();
			json.put(jsonObj.getJSONObject("intervention"));
		}
		int nb = json.length();
		
		JSONObject current;
		SimpleIntervention i;
		for(int j = 0;j<nb;j++) {
			current = json.getJSONObject(j);
			i = new SimpleIntervention();
			i.setId(current.getLong("id"));
			i.setName(current.getString("name"));
			i.setDateStart(current.getString("dateStart"));
			i.setDateEnd(current.getString("dateEnd"));
			i.setStatus(current.getInt("status"));
			
			list.add(i);
		}
	}
}
