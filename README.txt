Java Web Server
===============

A simple multi-threaded HTTP 1.0 Server implemented in Java for educational 
purposes based on W3C specifications (http://www.w3.org/Protocols/):

* [W3](https://www.w3.org/Protocols/HTTP/AsImplemented.html) Hypertext Transfer Protocol -- HTTP/0.9
* [RFC 1945](http://www.ietf.org/rfc/rfc1945.txt) Hypertext Transfer Protocol -- HTTP/1.0
* [RFC 2616](http://www.ietf.org/rfc/rfc2616.txt) Hypertext Transfer Protocol -- HTTP/1.1

This project has been enhanced with modern features and a robust testing suite.

For a more reliable solution, please check Jigsaw, W3C's Java HTTP server
implementation (http://www.w3.org/Jigsaw/), or other existing open-source Java
web servers.

This software is released under the FreeBSD license.

[Author info and contact](http://dasanjos.com) 

FEATURES
========

* **HTTP 1.0 Compatibility:** Supports `GET`, `HEAD`, and `POST` methods.
* **Security:** Includes protection against path traversal attacks.
* **Directory Listing:** Generates directory listings with parent directory links.
* **Default Files:** Automatically serves `index.html` when a directory is requested.
* **Error Handling:** Returns `404 Not Found`, `500 Internal Server Error`, and `501 Not Implemented` status codes where appropriate.
* **Logging:** Uses SLF4J and Logback for robust and configurable logging.

RUNNING
=======

The Java Web Server runs on any operating system with Java (JRE) 1.8+ installed.

To start the Java Web Server, download the application jar file and run the following command:

    $ java -jar web-server.jar <PORT_NUMBER>
 
To stop the server, press `Ctrl+C` on the command line.
(The `PORT_NUMBER` parameter is optional; the default port is 8080)

COMPILING
=========

**Step 1 - Install Requirements**
-----------------------------
To download and compile the source code, you need to install:

- Java SDK v1.8+
- Maven v3.0+
- Git v1.7+

**Step 2 - Get the source code**
----------------------------
Download the project source code from the GitHub repository:

    $ git clone https://github.com/dasanjos/java-WebServer

This will create a folder named "java-WebServer" containing the project source files.

**Step 3 - Compile it!**
--------------------
Go to the project root folder and run the following command:

    $ mvn clean package

This will create a "target" folder containing the application jar file: `java-WebServer-0.2-jar-with-dependencies.jar`

TESTING
=======

The project includes a comprehensive suite of unit and integration tests. To run all tests, execute the following command in the project root:

    $ mvn test
