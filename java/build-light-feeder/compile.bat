call setenv.bat

javac.exe -classpath ./lib/comm.jar -sourcepath ./src -d ./classes ./src/serialtalk/*.java
xcopy /Y .\src\META-INF\*.* .\classes\META-INF\

pause