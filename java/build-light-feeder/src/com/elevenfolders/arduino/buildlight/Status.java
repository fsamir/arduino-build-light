package com.elevenfolders.arduino.buildlight;


public enum Status {
    BUILDING(0),
    FAILED(-1),
    SUCCESS(1),
    BUILDING_FROM_SUCCESS(2),
    BUILDING_FROM_FAILURE(-2);

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
}
