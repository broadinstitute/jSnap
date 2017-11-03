package org.broadinstitute.jsnap;

import android.os.AsyncTask;
import android.util.Log;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by Osiris on 10/31/2017.
 */

public class AsyncRequest extends AsyncTask<URL, Void, String[]> {
    private String logtag = "AsyncRequestTask";
//    private String AUTH = "gaagbot:bender";

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
    }

    String[] resultArray;
    @Override
    protected String[] doInBackground(URL... urls) {

            try {
                // Creating & connection Connection with url and required Header.
                HttpURLConnection urlConnection = (HttpURLConnection) urls[0].openConnection();
//                urlConnection.setRequestProperty("Content-Type", "application/json");
//                urlConnection.setRequestProperty("header-param_3", "value-3");
//                urlConnection.setRequestProperty("header-param_4", "value-4");
//                urlConnection.setRequestProperty("Authorization", "Basic Y2tfNDIyODg0NWI1YmZiZT1234ZjZWNlOTA3ZDYyZjI4MDMxY2MyNmZkZjpjc181YjdjYTY5ZGM0OTUwODE3NzYwMWJhMmQ2OGQ0YTY3Njk1ZGYwYzcw");
                urlConnection.setRequestMethod("GET");   //POST or GET
                urlConnection.connect();

                // Create JSONObject Request
//                JSONObject jsonRequest = new JSONObject();
//                jsonRequest.put("username", "user.name");
//                jsonRequest.put("password", "pass@123");

                // Write Request to output stream to server.
//                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
//                out.write(jsonRequest.toString());
//                out.close();

                // Check the connection status.
                int statusCode = urlConnection.getResponseCode();
                String statusMsg = urlConnection.getResponseMessage();

            } catch (Exception e) {
                Log.e(logtag, e.toString());
            }
            return resultArray;
        }



    protected void onPostExecute(String[] result){
    }
}
