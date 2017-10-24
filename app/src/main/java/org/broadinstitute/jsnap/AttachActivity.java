package org.broadinstitute.jsnap;
import android.app.Activity;
import android.os.Bundle;
import android.util.Base64;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
/**
 * Created by Amr on 10/20/2017.
 */

public class AttachActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_attach);
        //TODO: Get a list of all projects available on the JIRA instance.
        //TODO: Populate the projects autocomplete so user can select projects via api call. Should use autocomplete.
        //TODO: On selection of Projects autocomplete, populate the issues drop down via api call. Should use autocomplete.
        //TODO: Once issue is selected, make attach button available.
        //TODO: On click of attach button, make API call to attach image to ticket.
        //TODO: Send a toast message with result
    }

    public static void main(String[] args) {
        URL url;
        try {
            String user_pass = "amr:Mohandaseen75";
            Base64.encode(user_pass.getBytes(), Base64.DEFAULT);
            url = new URL("https://btljira.broadinstitute.org/rest/api/2/issue/NOTREAL-20");
            HttpURLConnection  connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", "basic " +
                    Base64.encode(user_pass.getBytes(), Base64.NO_WRAP));
            InputStream content = (InputStream)connection.getInputStream();
            BufferedReader in   =
                    new BufferedReader (new InputStreamReader (content));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}

