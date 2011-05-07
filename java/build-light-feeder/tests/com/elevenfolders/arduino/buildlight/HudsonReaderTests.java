package com.elevenfolders.arduino.buildlight;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.List;

public class HudsonReaderTests {

    @Test
    public void testGetFailedStatus() {

        String jobsAsString = JobsBuilder.getInstance().with(Status.FAILED).buildAsJson();

        HudsonReader reader = new HudsonReader(null);
        reader.setJson(jobsAsString);


        Status status = reader.getStatus();

        Assert.assertEquals(Status.FAILED, status);

    }

    @Test
    public void testUpdateFromServer() {
//        HudsonReader reader = new HudsonReader("http://deadlock.netbeans.org/hudson/job/analytics-server/api/json");
        HudsonReader reader = new HudsonReader("http://deadlock.netbeans.org/hudson/job/faqsuck/api/xml");
        reader.updateFromServer();


        Status status = reader.getStatus();
        Assert.assertNotNull(status);
    }
}
