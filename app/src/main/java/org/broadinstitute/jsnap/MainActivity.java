package org.broadinstitute.jsnap;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Amr on 10/20/2017.
 */

public class MainActivity extends Activity {
    private static int TAKE_PICTURE = 1;
    final String logtag = "MainActivity";

    private static final String AUTHORITY = BuildConfig.APPLICATION_ID+".fileprovider";

    private static final String PHOTOS="photos";
    private static final String FILENAME="jsnap_test.jpg";
    Uri imageURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button retakeButton = findViewById(R.id.retake_button);

        retakeButton.setOnClickListener(cameraListener);

        try {
            JsonRequestTask jiraProjectTask = new JsonRequestTask(
                    BuildConfig.Projects_URL,
                    "root");
            jiraProjectTask.execute();
            ArrayList<String> projects = jiraProjectTask.getResults();
            ArrayAdapter<String> projectsAdapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_dropdown_item_1line, projects);
            AutoCompleteTextView textView = findViewById(R.id.jira_projects);
            textView.setAdapter(projectsAdapter);
            textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selected = (String) parent.getItemAtPosition(position);
                    String issueUrlString = BuildConfig.JiraIssuesBaseUrl + selected + "&fields=key";
                    JsonRequestTask jiraIssueTask = new JsonRequestTask(issueUrlString,"issues");
                    jiraIssueTask.execute();
                    ArrayList<String> issues = jiraIssueTask.getResults();
                    ArrayAdapter<String> issuesAdapter = new ArrayAdapter<>(MainActivity.this,
                            android.R.layout.simple_dropdown_item_1line, issues);
                    AutoCompleteTextView textView2 = findViewById(R.id.jira_issues);
                    textView2.setAdapter(issuesAdapter);
                }
            });
        } catch (Exception e) {
            Log.e(logtag, e.toString());
        }
    }
    private View.OnClickListener cameraListener = new View.OnClickListener() {
        public void onClick(View v) {
            try {
                takePhoto();
            } catch (Exception e) {
                Log.e(logtag, e.toString());
            }
        }
    };

    private void takePhoto(){
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File imagePath = new File(getFilesDir(), "images");
        File photo = new File(imagePath, FILENAME);
        imageURI = FileProvider.getUriForFile(this, AUTHORITY, photo);
        Log.i(logtag, imageURI.getPath());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageURI);
        // TAKE_PICTURE is a request code saying we want to use the rear-facing camera.
        startActivityForResult(intent, TAKE_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if(resultCode == Activity.RESULT_OK) {
            Uri selectedImage = imageURI;
            getContentResolver().notifyChange(selectedImage, null);

            ImageView imageView = (ImageView)findViewById(R.id.image_camera);
            ContentResolver cr = getContentResolver();
            Bitmap bitmap;
            //TODO: Need to be able to find the image file.
            try {
                bitmap = MediaStore.Images.Media.getBitmap(cr, selectedImage);
                imageView.setImageBitmap(bitmap);
                Toast.makeText(MainActivity.this, selectedImage.toString(),
                        Toast.LENGTH_SHORT).show();
            } catch(Exception e) {
                Log.e(logtag, selectedImage.getEncodedPath());
                Log.e(logtag, e.toString());
                Log.e(logtag, "exception", e);
            }
        }
    }
}

