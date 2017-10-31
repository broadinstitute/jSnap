package org.broadinstitute.jsnap;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Amr on 10/20/2017.
 */

public class AttachActivity extends Activity implements AsyncResponse{
    private static String logtag = "AttachActivity";
    String[] projects;
    AsyncRequestTask asyncRequest = new AsyncRequestTask();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_attach);

        asyncRequest.listener = this;

        Button retakeButton = (Button)(findViewById(R.id.retake_button));
        retakeButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(AttachActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //TODO: Populate the projects autocomplete so user can select projects via api call. Should use autocomplete.
            URL rpc_url;
            String AUTH = "gaagbot:bender";
            try {
                rpc_url = new URL("https://btljira.broadinstitute.org/rpc/json-rpc/excel2jirasoapservice/getAllProjects");
                asyncRequest.execute(rpc_url);

            } catch (Exception e) {
                Log.e(logtag, e.toString());
            }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, projects);
        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.jira_projects);
        textView.setAdapter(adapter);
        //TODO: On selection of Projects autocomplete, populate the issues drop down via api call. Should use autocomplete.
        //TODO: Once issue is selected, make attach button available.
        //TODO: On click of attach button, make API call to attach image to ticket.
        //TODO: Send a toast message with result
    }
    @Override
    public void processFinish(String[] output){

    }
    //TODO: Get a list of all projects available on the JIRA instance.
}


//    private class GetProjectsTask extends AsyncTask<String[], Void, String> {
//
//        @Override
//        protected String[] doInBackground(String[] params) {
//            URL rpc_url;
//            String AUTH = "gaagbot:bender";
//            String[] projArray;
//            try {
//                rpc_url = new URL("https://btljira.broadinstitute.org/rpc/json-rpc/excel2jirasoapservice/getAllProjects");
//                HttpURLConnection  connection = (HttpURLConnection) rpc_url.openConnection();
//                connection.setRequestMethod("POST");
//                connection.setDoOutput(true);
//                connection.setRequestProperty("Authorization", "basic " +
//                        Base64.encode(AUTH.getBytes(), Base64.NO_WRAP));
//                Log.i(logtag, Integer.toString(connection.getResponseCode()));
//                Log.i(logtag, connection.getResponseMessage());
//                InputStream content = (InputStream)connection.getInputStream();
//                BufferedReader in = new BufferedReader (new InputStreamReader (content));
//                String line;
//                while ((line = in.readLine()) != null) {
//                    Log.i(logtag, line);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                Log.e(logtag, e.toString());
//            }
//            return projArray;
//        }
//
//
//        @Override
//        protected void onPostExecute(String message) {
//            //process message
//        }
//
//    }


//
//
//    public static void main(String[] args) {
//        URL url;
//        try {
//            String user_pass = "gaagbot:bender";
//            Base64.encode(user_pass.getBytes(), Base64.DEFAULT);
//            url = new URL("https://btljira.broadinstitute.org/rest/api/2/issue/NOTREAL-20");
//            HttpURLConnection  connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("POST");
//            connection.setDoOutput(true);
//            connection.setRequestProperty("Authorization", "basic " +
//                    Base64.encode(user_pass.getBytes(), Base64.NO_WRAP));
//            InputStream content = (InputStream)connection.getInputStream();
//            BufferedReader in   =
//                    new BufferedReader (new InputStreamReader (content));
//            String line;
//            while ((line = in.readLine()) != null) {
//                System.out.println(line);
//            }
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }
