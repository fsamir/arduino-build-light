package com.elevenfolders.arduino.buildlight;


public enum Status {
  SUCCESS(1),
  FAILED(2),
  BUILDING(3),
  BUILDING_FROM_SUCCESS(4),
  BUILDING_FROM_FAILURE(5),
  DISABLED(6),
  INDEFINITE(9);

  private int status;
  private String url;

  Status(int status) {
    this.status = status;
  }
  Status(int status, String url) {
    this.status = status;
    this.url = url;
  }

  public String toString() {
    return Integer.toString(status);
  }

  public int getCode() {
    return status;
  }
}
