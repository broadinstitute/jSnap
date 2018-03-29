package org.broadinstitute.jsnap;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InterfaceAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Created by Osiris on 10/31/2017.
 */

public class AsyncRequest extends AsyncTask<URL, Void, List<String>> {
    private String logtag = "AsyncRequestTask";
    public AsyncResponse delegate;
    List<String> projects = new ArrayList<String>();

    @Override
    protected void onPreExecute(){

        super.onPreExecute();
    }

    private String buildBasicAuthorizationString(String username, String password) {

        String credentials = username + ":" + password;
        return "Basic " + new String(Base64.encode(credentials.getBytes(), Base64.DEFAULT));
    }

    @Override
    protected List<String> doInBackground(URL... urls) {
            try {
                // Creating & connection Connection with url and required Header.
                HttpURLConnection urlConnection = (HttpURLConnection) urls[0].openConnection();
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Authorization", buildBasicAuthorizationString("gaagbot", "bender"));
                urlConnection.setRequestMethod("GET");   //POST or GET
                // CODE HANGS HERE
                urlConnection.connect();
                StringBuilder sb = new StringBuilder();
                int responseCode = urlConnection.getResponseCode();
                if (responseCode == 200) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    String output;
                    while ((output = br.readLine()) != null) {
                        sb.append(output);
                    }
                }
                JSONObject json = new JSONObject(sb.toString());
//                for (int i = 0; i < )
//                json.getJSONArray();
                boolean test = json.has("key");
                String responseMessage = urlConnection.getResponseMessage();
                projects.add(responseMessage);
            } catch (Exception e) {
                Log.e(logtag, e.toString());
            }
            return projects;
        }


    @Override
    protected void onPostExecute(List<String> result){
        delegate.processFinish(result);
    }
}
