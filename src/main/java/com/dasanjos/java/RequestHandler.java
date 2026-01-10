package com.dasanjos.java;

import com.dasanjos.java.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.Socket;
import com.dasanjos.java.http.HttpResponse;

/**
 * Class <code>RequestHandler</code> - Thread class that answer the requests in the socket
 */
public class RequestHandler implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(RequestHandler.class);

	private final Socket socket;
	private final File webRoot;

	public RequestHandler(Socket socket, File webRoot) {
		this.socket = socket;
		this.webRoot = webRoot;
	}

	public void run() {
		try {
			HttpRequest req = new HttpRequest(socket.getInputStream());
			HttpResponse res = new HttpResponse(req, webRoot);
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
