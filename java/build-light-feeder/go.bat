REM use RXTX com library
@rem MUST USE \\ path separator for comm.jar file
set cp=.\\lib\\comm.jar;lib/RXTXcomm.jar

set cp=%cp%;./lib/serialfeeder.jar

REM Run application
java.exe -cp %cp% -Djava.library.path=lib com.elevenfolders.arduino.buildlight.SerialFeeder

pause
