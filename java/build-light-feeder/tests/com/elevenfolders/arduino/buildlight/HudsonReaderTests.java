package com.elevenfolders.arduino.buildlight;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

public class HudsonReaderTests {

    @Test
    public void testGetStatusFromSuccessStatus() {
//        HudsonReader reader = new HudsonReader("http://deadlock.netbeans.org/hudson/job/faqsuck/api/xml");
        HudsonReader reader = EasyMock.createMock(HudsonReader.class);

        Status status = reader.getStatus();
        Assert.assertNotNull(status);
    }

  //TODO: Change this test to produce an XML rather than Json.
//  @Test
//  public void testGetFailedStatus() {
//    String jobsAsString = JobsBuilder.getInstance().with(Status.FAILED).buildAsXml();
//    HudsonReader reader = new HudsonReader(null);
//    reader.setXml(jobsAsString);
//    Status status = reader.getStatus();
//    Assert.assertEquals(Status.FAILED, status);
//  }
}
