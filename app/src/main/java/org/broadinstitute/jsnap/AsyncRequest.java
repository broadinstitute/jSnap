package org.broadinstitute.jsnap;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.codesnippets4all.json.parsers.JSONParser;
import com.codesnippets4all.json.parsers.JsonParserFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
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

public class AsyncRequest extends AsyncTask<URL, Void, StringBuilder> {
    private String logtag = "AsyncRequestTask";
    public AsyncResponse delegate;
    StringBuilder sb = new StringBuilder();
    @Override
    protected void onPreExecute(){

        super.onPreExecute();
    }

    private String buildBasicAuthorizationString(String username, String password) {

        String credentials = username + ":" + password;
        return "Basic " + new String(Base64.encode(credentials.getBytes(), Base64.DEFAULT));
    }

    @Override
    protected StringBuilder doInBackground(URL... urls) {
            try {
                // Creating & connection Connection with url and required Header.
                HttpURLConnection urlConnection = (HttpURLConnection) urls[0].openConnection();
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Authorization", buildBasicAuthorizationString("gaagbot", "bender"));
                urlConnection.setRequestMethod("GET");   //POST or GET
                // CODE HANGS HERE
                urlConnection.connect();
                int responseCode = urlConnection.getResponseCode();
                if (responseCode == 200) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    String output;
                    while ((output = br.readLine()) != null) {
                        sb.append(output);
                    }
                } else {
                    String not200 = responseCode + ": " + urlConnection.getResponseMessage();
                    Log.e(logtag, not200);
                }
            } catch (Exception e) {
                Log.e(logtag, e.toString());
            }
            return sb;
        }


    @Override
    protected void onPostExecute(StringBuilder result){
        delegate.processFinish(result);
    }
}
