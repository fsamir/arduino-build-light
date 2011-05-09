package com.elevenfolders.arduino.buildlight;

import org.junit.Assert;
import org.junit.Test;

public class JobsTests {

  @Test
  public void testgetPrevalentStatusForFailedJob(){
    Jobs jobs = new Jobs();
    jobs.add(new Job(Status.FAILED));
    jobs.add(new Job(Status.SUCCESS));
    jobs.add(new Job(Status.BUILDING_FROM_FAILURE));
    jobs.add(new Job(Status.BUILDING_FROM_SUCCESS));

    Assert.assertEquals(Status.FAILED, jobs.getPrevalentStatus());
  }
  @Test
  public void testgetPrevalentStatusForBuildingFromFailure(){
    Jobs jobs = new Jobs();
    jobs.add(new Job(Status.SUCCESS));
    jobs.add(new Job(Status.BUILDING_FROM_FAILURE));
    jobs.add(new Job(Status.BUILDING_FROM_SUCCESS));

    Assert.assertEquals(Status.BUILDING_FROM_FAILURE, jobs.getPrevalentStatus());
  }

  @Test
  public void testgetPrevalentStatusForBuildingFromSuccess(){
    Jobs jobs = new Jobs();
    jobs.add(new Job(Status.SUCCESS));
    jobs.add(new Job(Status.BUILDING_FROM_SUCCESS));

    Assert.assertEquals(Status.BUILDING_FROM_SUCCESS, jobs.getPrevalentStatus());
  }

  @Test
  public void testgetPrevalentStatusForSuccessJob(){
    Jobs jobs = new Jobs();
    jobs.add(new Job(Status.SUCCESS));

    Assert.assertEquals(Status.SUCCESS, jobs.getPrevalentStatus());
  }
}
