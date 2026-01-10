package com.dasanjos.java;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class <code>WebServer</code> - Main class that starts the Web Server Thread Pool in port 8080 (default)
 */
public class WebServer {

    private static final Logger LOGGER = Logger.getLogger(WebServer.class.getName());

    private static final int DEFAULT_PORT = 8080;

    private static final int N_THREADS = 3;

    private volatile boolean running = true;

    public static void main(String args[]) {
        try {
            new WebServer().start(getValidPortParam(args));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Startup Error", e);
            System.exit(1);
        }
    }

	public void start(int port) throws IOException {
		ServerSocket s = new ServerSocket(port);
        LOGGER.log(Level.INFO, "Web server listening on port " + port + "\r\nPress CTRL-C to quit");
		final ExecutorService executor = Executors.newFixedThreadPool(N_THREADS);
        //Add Graceful Shutdown
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                running = false;
                LOGGER.log(Level.INFO, "Shutting down server...");
                executor.shutdown();
            }
        });
		while (running) {
			executor.submit(new RequestHandler(s.accept()));
		}
	}

	/**
	 * Parse command line arguments (string[] args) for valid port number
	 * 
	 * @return int valid port number or default value (8080)
	 */
    static int getValidPortParam(String args[]) throws NumberFormatException {
        if (args.length > 0) {
            int port = Integer.parseInt(args[0]);
            if (port > 0 && port <= 65535) {
                if (port < 1024) {
                    LOGGER.log(Level.WARNING, "Port " + port + " may require administrator privileges");
                }
                return port;
            } else {
                throw new NumberFormatException("Invalid port! Port must be a number between 1 and 65535");
            }
        }
        return DEFAULT_PORT;
    }
}
