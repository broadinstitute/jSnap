package org.broadinstitute.jsnap;
import android.app.Activity;
import android.os.Bundle;

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
}

