package org.broadinstitute.jsnap;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import java.net.URL;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by Amr on 10/20/2017.
 */

public class AttachActivity extends Activity {
    private static String logtag = "AttachActivity";
    String[] projects;
// https://stackoverflow.com/questions/12575068/how-to-get-the-result-of-onpostexecute-to-main-activity-because-asynctask-is-a
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_attach);

        Button retakeButton = (Button)(findViewById(R.id.retake_button));
        retakeButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(AttachActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //TODO: Populate the projects autocomplete so user can select projects via api call. Should use autocomplete.
            URL url;
            try {
                AsyncRequest asyncRequest = new AsyncRequest();
//                url = new URL("https://btljira.broadinstitute.org/rpc/json-rpc/excel2jirasoapservice/getAllProjects");
//                url = new URL("https://btljira.broadinstitute.org/rest/api/2/mypermissions");
                url = new URL("http://gscid-cromwell.broadinstitute.org:9000/api/workflows/v1/query");
                asyncRequest.execute(url);
                asyncRequest.getStatus();
                String[] projects = asyncRequest.get();
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_dropdown_item_1line, projects);
                AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.jira_projects);
                textView.setAdapter(adapter);
            } catch (Exception e) {
                Log.e(logtag, e.toString());
            }

        //TODO: On selection of Projects autocomplete, populate the issues drop down via api call. Should use autocomplete.
        //TODO: Once issue is selected, make attach button available.
        //TODO: On click of attach button, make API call to attach image to ticket.
        //TODO: Send a toast message with result
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
