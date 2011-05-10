package com.elevenfolders.arduino.buildlight;

import java.util.ArrayList;

public class Jobs extends ArrayList<Job> {
  /**
   * Specifies the verification priority.
   */
  private Status[] priority = new Status[]{
          Status.FAILED,
          Status.BUILDING_FROM_FAILURE,
          Status.BUILDING_FROM_SUCCESS,
          Status.BUILDING,
          Status.SUCCESS
  };

  /**
   * Return the current status of the build server, given the following priority:
   *
   * @return the overall current Status of the build server.
   */
  public Status getPrevalentStatus() {
    for (Status status: priority) {
      for(Job job:this) {
        if(job.getStatus().equals(status)){
          if(status != Status.SUCCESS) {
            System.out.println("\nFailing job: "+ job.getUrl());
          }
          return job.getStatus();
        }
      }
    }
    return Status.INDEFINITE;
  }

}
