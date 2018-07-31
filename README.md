gene-data-analysis
==================

This project loads gene microarray data to NeoCore XML database for the purpose
of analysis. The file presentation.pdf has more information.

 

Unit Test
=========

PATH=\$PATH:/usr/java/j2sdk1.4.0/bin

 

javac -classpath “./lib/neoHTTPConnection.jar" \*.java

 

time java -Xmx1073741824 -classpath “.:./lib/neoHTTPConnection.jar" CallLoad

 

Deploy
======

Copy files to the server using copy.bat file

 

Running Data Load on the Server
===============================

Run on the server using linux script CallLoad.sh

 

Start INIA
==========

Start INIA using the file INIA.bat.

 

 

 
