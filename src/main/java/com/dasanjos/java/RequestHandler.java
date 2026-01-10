package com.dasanjos.java;

import com.dasanjos.java.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Socket;
import com.dasanjos.java.http.HttpResponse;

/**
 * Class <code>RequestHandler</code> - Thread class that answer the requests in the socket
 */
public class RequestHandler implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(RequestHandler.class);

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
			LOGGER.error("Runtime Error", e);
		} finally {
			if (socket != null && !socket.isClosed()) {
				try {
					socket.close();
				} catch (Exception e) {
					LOGGER.warn("Error closing socket", e);
				}
			}
    	}
	}
}
