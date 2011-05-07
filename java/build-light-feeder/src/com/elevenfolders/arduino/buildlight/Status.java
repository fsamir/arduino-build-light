package com.elevenfolders.arduino.buildlight;


public enum Status {
    SUCCESS(1),
    FAILED(2),
    BUILDING(3),
    BUILDING_FROM_SUCCESS(4),
    BUILDING_FROM_FAILURE(5),
    DISABLED(6);

    private int status;

    Status(int status){
        this.status = status;
    }
    public String toString(){
        return Integer.toString(status);
    }

    public int getCode() {
        return status;
    }

    public class DISABLED {
    }
}
