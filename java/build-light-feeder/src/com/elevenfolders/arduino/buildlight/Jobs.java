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

  public Status getPrevalentStatus() {
    for (Status status: priority) {
      if (this.contains(new Job(status))) {
        return status;
      }
    }
    return Status.INDEFINITE;
  }

}
