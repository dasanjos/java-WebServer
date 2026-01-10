package com.dasanjos.java;

import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.Socket;

import static org.mockito.Mockito.when;
import static org.junit.Assert.assertTrue;

public class RequestHandlerTest {

    @Test
    public void testRequestHandler() throws Exception {
        // Create a mock socket
        Socket mockSocket = Mockito.mock(Socket.class);

        // Create input and output streams
        String request = "GET / HTTP/1.1\n\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(request.getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Stub the socket's getInputStream and getOutputStream methods
        when(mockSocket.getInputStream()).thenReturn(inputStream);
        when(mockSocket.getOutputStream()).thenReturn(outputStream);

        // Create a RequestHandler and run it
        RequestHandler requestHandler = new RequestHandler(mockSocket);
        requestHandler.run();

        // Verify that the response contains a valid HTTP status line
        String response = outputStream.toString("UTF-8");
        assertTrue("Response should contain a valid HTTP status line", response.startsWith("HTTP/1.0 200 OK"));

        // Verify that the socket was closed
        Mockito.verify(mockSocket).close();
    }
}
