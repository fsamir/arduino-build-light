package com.elevenfolders.arduino.buildlight;

import java.util.Arrays;
import java.util.List;

public class JobsBuilder {

    private static JobsBuilder instance;
    private Status status;

    private JobsBuilder() {

    }
    public static JobsBuilder getInstance() {
        if(instance == null){
            instance = new JobsBuilder();
        }
        return instance;
    }

    public JobsBuilder with(Status status) {
        this.status = status;
        return this;
    }

    public List<Job> build() {
        Job job = new Job(this.status);
        return Arrays.asList(new Job[]{job});
    }

    public String buildAsXml() {
        return null;
    }
}
