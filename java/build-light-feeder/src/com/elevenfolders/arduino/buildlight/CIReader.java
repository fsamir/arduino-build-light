package com.elevenfolders.arduino.buildlight;

public interface CIReader {
    Status getStatus();

    void updateFromServer();
}
