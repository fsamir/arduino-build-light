package serialtalk;

//import gnu.io.CommPortIdentifier;
//import gnu.io.SerialPort;
//import processing.app.Preferences;

import javax.comm.CommPortIdentifier;
import javax.comm.SerialPort;
import java.io.InputStream;
import java.io.OutputStream;

public class Main {
  static InputStream input;
  static OutputStream output;


  public static void main(String[] args) throws Exception {
//    Preferences.init();
//    String port = Preferences.get("serial.port");
//    int serialDebugRate = Preferences.getInteger("serial.debug_rate");
    int serialDebugRate = 9600;
    String portName = "COM4";
    System.out.println("Using port: " + portName);
    CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier(portName);

    SerialPort port = (SerialPort) portId.open("serial talk", 4000);
    input = port.getInputStream();
    output = port.getOutputStream();
    port.setSerialPortParams(serialDebugRate,
                             SerialPort.DATABITS_8,
                             SerialPort.STOPBITS_1,
                             SerialPort.PARITY_NONE);
    while (true) {
      while (input.available() > 0) {
        System.out.print((char) (input.read()));
      }
    }
  }
}