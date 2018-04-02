package org.broadinstitute.jsnap;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by Amr on 10/20/2017.
 */

public class AttachActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //
        super.onCreate(savedInstanceState);
        String logtag = "AttachActivity";
        setContentView(R.layout.activty_attach);
        Button retakeButton = findViewById(R.id.retake_button);

        retakeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AttachActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        try {
            JiraProjectsTask jiraProjectTask = new JiraProjectsTask("https://btljira.broadinstitute.org/rest/api/2/project");
            jiraProjectTask.execute();
            ArrayList<String> projects = jiraProjectTask.getResults();
            final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, projects);
            AutoCompleteTextView textView = findViewById(R.id.jira_projects);
            textView.setAdapter(adapter);
        } catch (Exception e) {
            Log.e(logtag, e.toString());
        }
    }
}

