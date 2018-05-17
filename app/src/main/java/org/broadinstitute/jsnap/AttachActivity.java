package org.broadinstitute.jsnap;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
        final String logtag = "AttachActivity";
        setContentView(R.layout.activty_attach);
        Button retakeButton = findViewById(R.id.retake_button);

        retakeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AttachActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        try {
            JsonRequestTask jiraProjectTask = new JsonRequestTask(
                    "https://btljira.broadinstitute.org/rest/api/2/project",
                    "root");
            jiraProjectTask.execute();
            ArrayList<String> projects = jiraProjectTask.getResults();
            ArrayAdapter<String> projectsAdapter = new ArrayAdapter<>(AttachActivity.this,
                android.R.layout.simple_dropdown_item_1line, projects);
            AutoCompleteTextView textView = findViewById(R.id.jira_projects);
            textView.setAdapter(projectsAdapter);
            textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selected = (String) parent.getItemAtPosition(position);
                    //TODO: This guy isn't working properly, need to fix it so second dropdown in this view populates.
                    String issueUrlString = "https://btljira.broadinstitute.org/rest/api/2/search?jql=project=" + selected + "&fields=key";
                    JsonRequestTask jiraIssueTask = new JsonRequestTask(issueUrlString,"issues");
                    jiraIssueTask.execute();
                    ArrayList<String> issues = jiraIssueTask.getResults();
                    ArrayAdapter<String> issuesAdapter = new ArrayAdapter<>(AttachActivity.this,
                            android.R.layout.simple_dropdown_item_1line, issues);
                    AutoCompleteTextView textView2 = findViewById(R.id.jira_issues);
                    textView2.setAdapter(issuesAdapter);
                }
            });
        } catch (Exception e) {
            Log.e(logtag, e.toString());
        }
    }
}

