#set CP=./lib/comm.jar;./lib/RXTXcomm.jar;./lib/testcomm.jar

java -cp ./lib/core.jar:./lib/RXTXcomm.jar:./lib/serialfeeder.jar -Djava.library.path=lib com.elevenfolders.arduino.buildlight.SerialFeeder
