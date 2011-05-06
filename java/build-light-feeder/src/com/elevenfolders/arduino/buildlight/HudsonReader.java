package com.elevenfolders.arduino.buildlight;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Franklin Dattein
 * @since 1.0
 */
public class HudsonReader implements Server {

    public Status getStatus() {

        //json sample: http://deadlock.netbeans.org/hudson/api/json

        String jsonFromServer = "\"jobs\":[{\"name\":\"analytics-server\",\"url\":\"http://deadlock.netbeans.org/hudson/job/analytics-server/\",\"color\":\"disabled\"},{\"name\":\"apitest\",\"url\":\"http://deadlock.netbeans.org/hudson/job/apitest/\",\"color\":\"blue\"},{\"name\":\"ergonomics\",\"url\":\"http://deadlock.netbeans.org/hudson/job/ergonomics/\",\"color\":\"yellow_anime\"}]";

        Gson gson = new Gson(); // Or use new GsonBuilder().create();



        List<Job> jobs ;

        Type listType = new TypeToken<List<Job>>() {}.getType();
        jobs = gson.fromJson(jsonFromServer, listType);

        if (containsFailed(jobs)) {
            return Status.FAILED;
        } else if (containsBuilding(jobs)) {
            return Status.BUILDING;
        } else {
            return Status.SUCCESS;
        }
    }

    private boolean containsBuilding(List<Job> jobs) {
        return containsColor(jobs, "blue");

    }

    private boolean containsFailed(List<Job> jobs) {
        return containsColor(jobs, "yellow");
    }

    private boolean containsColor(List<Job> jobs, String color) {
        for (Job job : jobs) {
            if (job.getName() != null && job.getName().toLowerCase().startsWith(color.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
