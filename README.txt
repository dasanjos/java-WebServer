Java Web Server
===============

A simple multi-threaded HTTP 1.0 Server implemented in Java for educational 
purposes based on W3C specifications (http://www.w3.org/Protocols/):

* [W3](https://www.w3.org/Protocols/HTTP/AsImplemented.html) Hypertext Transfer Protocol -- HTTP/0.9
* [RFC 1945](http://www.ietf.org/rfc/rfc1945.txt) Hypertext Transfer Protocol -- HTTP/1.0
* [RFC 2616](http://www.ietf.org/rfc/rfc2616.txt) Hypertext Transfer Protocol -- HTTP/1.1
* [RFC 2617](http://www.ietf.org/rfc/rfc2617.txt) HTTP Authentication: Basic and Digest Access Authentication
* [RFC 6265](http://tools.ietf.org/html/rfc6265) HTTP State Management Mechanism (Cookies)

For a more reliable solution, please check Jigsaw, W3C Java HTTP 1.1 server
implementation (http://www.w3.org/Jigsaw/) or other existing open source Java  
web servers (http://java-source.net/open-source/web-servers).

This software is released under FreeBSD license.

[Author info and contact](http://dasanjos.com) 


RUNNING
=======

The Java Web Server runs on any Operational System with Java (JRE) 1.6+ installed 

To start the Java Web Server, download the application jar file and run the following command:

    $ java -jar web-server.jar <PORT_NUMBER>
 
To stop the Simple Web server, just press Cntr+C on the command line.
(The parameter PORT_NUMBER is optional, default port is 8080)


COMPILING
=========

Step 1 - Install Requirements
-----------------------------
To download and compile the source code you need to install: 

- Java SDK v1.6+
- Maven v2.0+
- Git v1.7+

Step 2 - Get the source code
----------------------------
Download the project source code from GitHub repository (https://github.com/dasanjos/java-WebServer) 

    $ git clone git://github.com/dasanjos/java-WebServer

This will create a folder "java-WebServer" containing the project source files. 

Step 3 - Compile it!
--------------------
Go to the project root folder and run the following command:

    $ mvn clean package

This will create a "target" folder containing the application jar file: java-WebServer-1.0-jar-with-dependencies.jar  
