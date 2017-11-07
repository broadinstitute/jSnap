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
import java.util.List;

/**
 * Created by Amr on 10/20/2017.
 */

public class AttachActivity extends Activity implements AsyncResponse {
    private static String logtag = "AttachActivity";

    AsyncRequest asyncRequest = new AsyncRequest();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        asyncRequest.delegate = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_attach);
        Button retakeButton = (Button) (findViewById(R.id.retake_button));

        retakeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AttachActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        try {
            URL url;
//                url = new URL("https://btljira.broadinstitute.org/rpc/json-rpc/excel2jirasoapservice/getAllProjects");
//                url = new URL("https://btljira.broadinstitute.org/rest/api/2/mypermissions");
//            url = new URL("http://btl-cromwell.broadinstitute.org:9000/api/engine/v1/version");
            url = new URL("https://httpbin.org/headers");
            asyncRequest.execute(url);
        } catch (Exception e) {
            Log.e(logtag, e.toString());
        }
    }

    @Override
    public void processFinish(List<String> output){
        try {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_dropdown_item_1line, output);
            AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.jira_projects);
            textView.setAdapter(adapter);
        } catch (Exception e) {
            Log.e(logtag, e.toString());
        }
    }
}

