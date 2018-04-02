package org.broadinstitute.jsnap;


import android.util.Log;
import com.codesnippets4all.json.parsers.JSONParser;
import com.codesnippets4all.json.parsers.JsonParserFactory;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JiraProjectsTask implements AsyncResponse {
    private static String logtag = "GetJiraProjectsTask";
    private AsyncRequest asyncRequest = new AsyncRequest();
    private ArrayList<String> projects = new ArrayList<>();

    public void execute()  {
        try {
            asyncRequest.delegate = this;
            URL projectURL;
            projectURL = new URL("https://btljira.broadinstitute.org/rest/api/2/project");
            asyncRequest.execute(projectURL);
        } catch (Exception e) {
            Log.e(logtag, e.toString());
        }
    }

    public ArrayList<String> getProjects() {
        return projects;
    }

    @Override
    public void processFinish(StringBuilder output){
         try {
             JsonParserFactory factory = JsonParserFactory.getInstance();
             JSONParser parser = factory.newJsonParser();
             Map jsonData = parser.parseJson(output.toString());
             ArrayList<HashMap<String, String>> mapList = (ArrayList<HashMap<String, String>>) jsonData.get("root");
             for (int i = 0; i < mapList.size(); i++) {
                 HashMap<String, String> m = mapList.get(i);
                 String project = m.get("key");
                 projects.add(project);
             }
         } catch (Exception e) {
             Log.e(logtag, e.toString());
         }
    }
}
