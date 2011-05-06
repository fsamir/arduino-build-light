call setenv.bat

SET MF=./classes/META-INF/MANIFEST.MF
jar.exe cvfm ./lib/testcomm.jar %MF% -C ./classes .

pause
