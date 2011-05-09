#call setenv.bat

MF=./classes/META-INF/MANIFEST.MF
export MF
jar cvfm ./lib/serialfeeder.jar $MF -C ./classes .

