package com.elevenfolders.arduino.buildlight;


public class Job {
  private String color;
  private String url;
  private String name;
  private Status status;

  public Job(Status s) {
    setStatus(s);
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public void setColor(Status status) {
    switch (status) {
      case FAILED:
        setColor("yellow");
        break;
      case SUCCESS:
        setColor("blue");
        break;
      case BUILDING:
        setColor("blue_anime");
        break;
      case BUILDING_FROM_FAILURE:
        setColor("orange_anime");
        break;
      case BUILDING_FROM_SUCCESS:
        setColor("blue_anime");
        break;
      default:
        setColor("blue");
        break;
    }
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public Status getStatus() {
    return this.status;
  }

  @Override
  public boolean equals(Object o) {
    Job job = (Job) o;
    if (job == null || job.getStatus() == null) {
      return false;
    }
    return this.getStatus().equals(job.getStatus());
  }

  @Override
  public int hashCode() {
    return getStatus().getCode();
  }
}
