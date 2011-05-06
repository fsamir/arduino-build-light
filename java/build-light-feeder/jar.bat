call setenv.bat

SET MF=./classes/META-INF/MANIFEST.MF
jar.exe cvfm ./lib/serialfeeder.jar %MF% -C ./classes .

pause
