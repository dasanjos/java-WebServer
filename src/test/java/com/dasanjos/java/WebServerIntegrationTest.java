package com.dasanjos.java;

import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import org.junit.Assert;

public class WebServerIntegrationTest {

    private WebServer webServer;
    private Thread serverThread;
    private int port;

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Before
    public void setUp() throws Exception {
        // Start the web server on a random free port
        webServer = new WebServer(tempFolder.getRoot());
        serverThread = new Thread(() -> {
            try {
                webServer.start(0); // 0 means a random available port
            } catch (IOException e) {
                if (!serverThread.isInterrupted()) {
                    e.printStackTrace();
                }
            }
        });
        serverThread.start();

        // Wait until the server has started and get the port
        do {
            Thread.sleep(100);
            port = webServer.getPort();
        } while (port == -1);
    }

    @After
    public void tearDown() {
        webServer.stop();
        serverThread.interrupt();
    }

    @Test
    public void testGetRequest() throws IOException {
        File testFile = tempFolder.newFile("test.txt");
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("Hello, world!");
        }

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet("http://localhost:" + port + "/test.txt");
        request.setProtocolVersion(HttpVersion.HTTP_1_0);
        try (CloseableHttpResponse response = client.execute(request)) {
            assertEquals(200, response.getStatusLine().getStatusCode());
            Assert.assertTrue("Response should contain Content-Length header", response.containsHeader("Content-Length"));
            Assert.assertTrue("Response should contain Allow header", response.containsHeader("Allow"));
        }
    }

    @Test
    public void testHeadRequest() throws IOException {
        File testFile = tempFolder.newFile("test.txt");
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("Hello, world!");
        }

        CloseableHttpClient client = HttpClients.createDefault();

        // Perform a GET request to get the expected headers
        HttpGet getRequest = new HttpGet("http://localhost:" + port + "/test.txt");
        getRequest.setProtocolVersion(HttpVersion.HTTP_1_0);
        CloseableHttpResponse getResponse = client.execute(getRequest);
        assertEquals(200, getResponse.getStatusLine().getStatusCode());

        // Perform a HEAD request
        HttpHead headRequest = new HttpHead("http://localhost:" + port + "/test.txt");
        headRequest.setProtocolVersion(HttpVersion.HTTP_1_0);
        try (CloseableHttpResponse headResponse = client.execute(headRequest)) {
            assertEquals(200, headResponse.getStatusLine().getStatusCode());
            // Assert that the HEAD response has no body
            assertEquals(null, headResponse.getEntity());
            // Assert that the headers are the same
            assertEquals(getResponse.getAllHeaders().length, headResponse.getAllHeaders().length);
        }
    }

    @Test
    public void testNotFound() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet("http://localhost:" + port + "/nonexistent.txt");
        request.setProtocolVersion(HttpVersion.HTTP_1_0);
        try (CloseableHttpResponse response = client.execute(request)) {
            assertEquals(404, response.getStatusLine().getStatusCode());
        }
    }

    @Test
    public void testDirectoryListing() throws IOException {
        tempFolder.newFile("test.txt");

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet("http://localhost:" + port + "/");
        request.setProtocolVersion(HttpVersion.HTTP_1_0);
        try (CloseableHttpResponse response = client.execute(request)) {
            assertEquals(200, response.getStatusLine().getStatusCode());
        }
    }

    @Test
    public void testPathTraversal() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet("http://localhost:" + port + "/../../../../../../../etc/passwd");
        request.setProtocolVersion(HttpVersion.HTTP_1_0);
        try (CloseableHttpResponse response = client.execute(request)) {
            assertEquals(403, response.getStatusLine().getStatusCode());
        }
    }

    @Test
    public void testDefaultFile() throws IOException {
        File indexFile = tempFolder.newFile("index.html");
        try (FileWriter writer = new FileWriter(indexFile)) {
            writer.write("<html><body>Hello</body></html>");
        }

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet("http://localhost:" + port + "/");
        request.setProtocolVersion(HttpVersion.HTTP_1_0);
        try (CloseableHttpResponse response = client.execute(request)) {
            assertEquals(200, response.getStatusLine().getStatusCode());
        }
    }

    @Test
    public void testPostRequest() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost request = new HttpPost("http://localhost:" + port + "/");
        request.setProtocolVersion(HttpVersion.HTTP_1_0);
        try (CloseableHttpResponse response = client.execute(request)) {
            assertEquals(501, response.getStatusLine().getStatusCode());
        }
    }
}
