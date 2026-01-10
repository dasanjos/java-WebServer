package com.dasanjos.java;

import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.dasanjos.java.http.HttpRequest;
import com.dasanjos.java.http.HttpResponse;

/**
 * Class <code>RequestHandler</code> - Thread class that answer the requests in the socket
 */
public class RequestHandler implements Runnable {

	private static final Logger LOGGER = Logger.getLogger(RequestHandler.class.getName());

	private Socket socket;

	public RequestHandler(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		try {
			HttpRequest req = new HttpRequest(socket.getInputStream());
			HttpResponse res = new HttpResponse(req);
			res.write(socket.getOutputStream());
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Runtime Error", e);
		} finally {
			if (socket != null && !socket.isClosed()) {
				try {
					socket.close();
				} catch (Exception e) {
					LOGGER.log(Level.WARNING, "Error closing socket", e);
				}
			}
    	}
	}
}
