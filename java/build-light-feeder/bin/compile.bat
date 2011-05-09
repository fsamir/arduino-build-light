call setenv.bat

javac.exe -classpath ./lib/comm.jar -sourcepath ./src -d ./classes ./src/com/elevenfolders/arduino/buildlight/*.java
xcopy /Y .\src\META-INF\*.* .\classes\META-INF\

pause