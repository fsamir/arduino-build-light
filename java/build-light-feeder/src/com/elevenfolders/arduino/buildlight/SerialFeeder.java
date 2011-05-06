package com.elevenfolders.arduino.buildlight;

import gnu.io.*;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Random;

public class SerialFeeder implements SerialPortEventListener {
    SerialPort serialPort;
    /**
     * Add heer the port name in use by  your Arduni IDE (Tools - > Serial Port)
     */
    private static final String PORT_NAMES[] = {
            "/dev/cu.usbserial-A4007mWg", // Mac OS X
            "/dev/ttyUSB0", // Linux
            "COM4", // Windows
    };
    /**
     * Buffered input stream from the port
     */
    private InputStream input;
    /**
     * The output stream to the port
     */
    private OutputStream output;
    /**
     * Milliseconds to block while waiting for port open
     */
    private static final int TIME_OUT = 2000;
    /**
     * Default bits per second for COM port.
     */
    private static final int DATA_RATE = 9600;
    Random random = new Random();
    private Status currentStatus;
    private Server server = new HudsonReader();

    public void initialize() throws NoSuchPortException {
        CommPortIdentifier portId = null;

        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
        // iterate through, looking for the port
        while (portEnum.hasMoreElements()) {
            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
            for (String portName : PORT_NAMES) {
                if (currPortId.getName().equals(portName)) {
                    portId = currPortId;
                    break;
                }
            }
        }

        if (portId == null) {
            System.out.println("Could not find COM port.");
            return;
        }

        try {
            // open serial port, and use class name for the appName.
            serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);

            // set port parameters
            serialPort.setSerialPortParams(DATA_RATE,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);

            // open the streams
            input = serialPort.getInputStream();
            output = serialPort.getOutputStream();

            // add event listeners
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);

            while (true) {
                Status newStatus = server.getStatus();
                System.out.println("Status: "+newStatus);
                if(newStatus != currentStatus) {
                    output.write(newStatus.getCode());
                    currentStatus = newStatus;
                }

                Thread.sleep(1000 * 3);
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    /**
     * This should be called when you stop using the port.
     * This will prevent port locking on platforms like Linux.
     */
    public synchronized void close() {
        if (serialPort != null) {
            serialPort.removeEventListener();
            serialPort.close();
        }
    }

    /**
     * Handle an event on the serial port. Read the data and print it.
     */
    public synchronized void serialEvent(SerialPortEvent oEvent) {
        if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
                int available = input.available();
                byte chunk[] = new byte[available];
                input.read(chunk, 0, available);

                // Displayed results are codepage dependent
                System.out.print(new String(chunk));
            } catch (Exception e) {
                System.err.println(e.toString());
            }
        }
        // Ignore all the other eventTypes, but you should consider the other ones.
    }

    public static void main(String[] args) throws Exception {
        SerialFeeder main = new SerialFeeder();
        main.initialize();
        System.out.println("Started");
    }
}
