package com.elevenfolders.arduino.buildlight;


import java.net.URL;

public class Job {
    private String color;
    private URL url;
    private String name;

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

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
