package org.broadinstitute.jsnap;


import android.util.Log;
import com.codesnippets4all.json.parsers.JSONParser;
import com.codesnippets4all.json.parsers.JsonParserFactory;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JsonRequestTask implements AsyncResponse {
    private static String logtag = "JsonRequestTask";
    private AsyncRequest asyncRequest = new AsyncRequest();
    private ArrayList<String> results = new ArrayList<>();
    private String urlString;
    private String key;

    JsonRequestTask(String urlString, String key) {
        this.urlString = urlString;
        this.key = key;
    }
    public void execute()  {
        try {
            asyncRequest.delegate = this;
            URL projectURL;
            projectURL = new URL(this.urlString);
            asyncRequest.execute(projectURL);
        } catch (Exception e) {
            Log.e(logtag, e.toString());
        }
    }

    public ArrayList<String> getResults() {
        return results;
    }

    @Override
    public void processFinish(StringBuilder output){
         try {
             JsonParserFactory factory = JsonParserFactory.getInstance();
             JSONParser parser = factory.newJsonParser();
             Map jsonData = parser.parseJson(output.toString());
             ArrayList<HashMap<String, String>> mapList;
             if (this.key != null){
                 mapList = (ArrayList<HashMap<String, String>>) jsonData.get(this.key);
             } else {
                 mapList = (ArrayList<HashMap<String, String>>) jsonData.values();
             }
             //TODO: this doesn't belong here as this is specific to a type of request.
             for (int i = 0; i < mapList.size(); i++) {
                 HashMap<String, String> m = mapList.get(i);
                 String project = m.get("key");
                 results.add(project);
             }

         } catch (Exception e) {
             Log.e(logtag, e.toString());
         }
    }
}
