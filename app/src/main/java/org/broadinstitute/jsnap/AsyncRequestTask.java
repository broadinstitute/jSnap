package org.broadinstitute.jsnap;

import android.os.AsyncTask;

import java.net.URL;

/**
 * Created by Osiris on 10/31/2017.
 */

public class AsyncRequestTask extends AsyncTask<URL, Void, String[]> {
    public AsyncResponse listener = null;
    
    public AsyncRequestTask(AsyncResponse listener){
        this.listener = listener;
    }
    
    @Override
    protected String[] doInBackground(URL... urls) {
        String[] proj = {"SSF", "TALES", "BTLHLP", "Submissions"};
        return proj;
    }

    @Override
    protected void onPostExecute(String[] result) {
        listener.processFinish(result);
    }
}
