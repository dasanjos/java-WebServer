package com.dasanjos.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class <code>WebServer</code> - Main class that starts the Web Server Thread Pool in port 8080 (default)
 */
public class WebServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebServer.class);

    private static final int DEFAULT_PORT = 8080;
    private static final int N_THREADS = 3;

    private final File webRoot;
    private volatile boolean running = true;
    private ServerSocket serverSocket;
    private ExecutorService executor;

    public WebServer() {
        this(new File("."));
    }

    public WebServer(File webRoot) {
        this.webRoot = webRoot;
    }

    public static void main(String args[]) {
        try {
            new WebServer().start(getValidPortParam(args));
        } catch (Exception e) {
            LOGGER.error("Startup Error", e);
            System.exit(1);
        }
    }

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        LOGGER.info("Web server listening on port {} \r\nPress CTRL-C to quit", getPort());
        executor = Executors.newFixedThreadPool(N_THREADS);

        // Add Graceful Shutdown
        Runtime.getRuntime().addShutdownHook(new Thread(() -> stop()));

        while (running) {
            try {
                executor.submit(new RequestHandler(serverSocket.accept(), webRoot));
            } catch (IOException e) {
                if (running) {
                    LOGGER.error("Error accepting connection", e);
                }
            }
        }
    }

    public void stop() {
        running = false;
        LOGGER.info("Shutting down server...");
        if (executor != null) {
            executor.shutdown();
        }
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            LOGGER.error("Error closing server socket", e);
        }
    }

    public int getPort() {
        return serverSocket != null ? serverSocket.getLocalPort() : -1;
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
                    LOGGER.warn("Port {} may require administrator privileges", port);
                }
                return port;
            } else {
                throw new NumberFormatException("Invalid port! Port must be a number between 1 and 65535");
            }
        }
        return DEFAULT_PORT;
    }
}
