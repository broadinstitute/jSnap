package org.broadinstitute.jsnap;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InterfaceAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Amr on 10/31/2017.
 */

public class AsyncRequest extends AsyncTask<URL, Void, List<String>> {
    private String logtag = "AsyncRequestTask";
    public AsyncResponse delegate;
    List<String> projects = new ArrayList<String>();

    @Override
    protected void onPreExecute(){

        super.onPreExecute();
    }

    @Override
    protected List<String> doInBackground(URL... urls) {
            try {
                // Creating & connection Connection with url and required Header.
                String up = "amr@broadinstitute.org:Mohandaseen75";
                HttpURLConnection urlConnection = (HttpURLConnection) urls[0].openConnection();
                byte[] encoded = Base64.encode(up.getBytes(), Base64.DEFAULT);
                String e = encoded.toString();
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Authorization", "Basic " + encoded);
                urlConnection.setRequestMethod("GET");   //POST or GET
                urlConnection.setRequestProperty("User-Agent", "Test");
                urlConnection.connect();
                int responseCode = urlConnection.getResponseCode();
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
