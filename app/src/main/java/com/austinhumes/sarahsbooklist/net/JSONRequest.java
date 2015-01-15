/*
 * @(#)JSONRequest.java
 */
package com.austinhumes.sarahsbooklist.net;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * <p>The <code>JSONRequest</code> class is a helper class that is used to handle JSON requests.</p>
 * From http://stackoverflow.com/questions/19837820/volley-jsonobjectrequest-post-request-not-working
 */
public class JSONRequest extends Request<JSONObject> {

	private Listener<JSONObject> listener;
	private Map<String, String> params;


	public JSONRequest(int method, String url, Map<String, String> params,
			Listener<JSONObject> reponseListener, ErrorListener errorListener) {
		super(method, url, errorListener);
		this.listener = reponseListener;
		this.params = params;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
		try {
			String jsonString = new String(response.data,
					HttpHeaderParser.parseCharset(response.headers));
			
			JSONObject responseJson = new JSONObject(jsonString);
			
			Log.d("booklist", "JSONRequest Received response: " + jsonString);
			Log.d("booklist", "response.statusCode: " + response.statusCode + ", headers: " + response.headers);
			for (String value : response.headers.keySet()) {
				Log.d("booklist", "keys: " + value + ": " + response.headers.get(value).toString());
			}
			
			if (responseJson.has("error")) {	// i.e. Subscription expired notice
				return Response.error(new VolleyError(responseJson.getString("error")));
			} else {
				return Response.success(responseJson,
						HttpHeaderParser.parseCacheHeaders(response));
			}
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JSONException je) {
			return Response.error(new ParseError(je));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		return params;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void deliverResponse(JSONObject jsonObject) {
		listener.onResponse(jsonObject);
	}
}
