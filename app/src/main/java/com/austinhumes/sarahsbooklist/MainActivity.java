package com.austinhumes.sarahsbooklist;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Pull list from api
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://austinhumes.com/sarah/get";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
            new Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    Log.d("blah", "Response is: " + jsonObject);
                }
            },
            new ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

            }
        });

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);

    }
    /*
    StringBuilder url = new StringBuilder("http://austinhumes.com/sarah/get");
    JSONRequest editTask = new JSONRequest(Request.Method.GET,
            url.toString(), null, successListener(), errorListener());

    DataService.getInstance().addToRequestQueue(editTask);
    */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*protected Listener<JSONObject> successListener() {
        return new Response.Listener<JSONObject>() {

            public void onResponse(JSONObject jsonObject) {

            }
        };
    }*/
}
