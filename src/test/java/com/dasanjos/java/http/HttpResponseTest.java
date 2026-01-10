package com.dasanjos.java.http;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class HttpResponseTest {

	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();

	@Test
	public void doHeadRequest() throws IOException {
		HttpRequest req = new HttpRequest(new ByteArrayInputStream("HEAD / HTTP/1.1\n\n".getBytes()));
		HttpResponse res = new HttpResponse(req, tempFolder.getRoot());

		Assert.assertTrue(res.headers.size() > 0);
		Assert.assertEquals(HttpResponse.VERSION + " " + Status._200, res.headers.get(0));
	}

	@Test
	public void doGetRequest() throws IOException {
		HttpRequest req = new HttpRequest(new ByteArrayInputStream("GET / HTTP/1.1\n\n".getBytes()));
		HttpResponse res = new HttpResponse(req, tempFolder.getRoot());

		Assert.assertTrue(res.headers.size() > 0);
		Assert.assertEquals(HttpResponse.VERSION + " " + Status._200, res.headers.get(0));
	}

	@Test
	public void unknownRequest() throws IOException {
		HttpRequest req = new HttpRequest(new ByteArrayInputStream("WHAT / HTTP/1.1\n\n".getBytes()));
		HttpResponse res = new HttpResponse(req, tempFolder.getRoot());

		Assert.assertTrue(res.headers.size() > 0);
		Assert.assertEquals(HttpResponse.VERSION + " " + Status._400, res.headers.get(0));
	}

	@Test
	public void pathTraversalRequest() throws IOException {
		HttpRequest req = new HttpRequest(new ByteArrayInputStream("GET ../../../../../../../etc/passwd HTTP/1.1\n\n".getBytes()));
		HttpResponse res = new HttpResponse(req, tempFolder.getRoot());

		Assert.assertTrue(res.headers.size() > 0);
		Assert.assertEquals(HttpResponse.VERSION + " " + Status._403, res.headers.get(0));
	}

	@Test
	public void directoryListingRequest() throws IOException {
		File testFile = tempFolder.newFile("test.txt");
		HttpRequest req = new HttpRequest(new ByteArrayInputStream("GET / HTTP/1.1\n\n".getBytes()));
		HttpResponse res = new HttpResponse(req, tempFolder.getRoot());
		String body = new String(res.body, StandardCharsets.UTF_8);

		Assert.assertTrue(res.headers.size() > 0);
		Assert.assertEquals(HttpResponse.VERSION + " " + Status._200, res.headers.get(0));
		Assert.assertTrue("Body should contain link to test.txt", body.contains(" <a href=\"/test.txt\">test.txt</a>".replace("/", File.separator)));
        boolean hasContentLength = false;
        boolean hasLastModified = false;
        for (String header : res.headers) {
            if (header.startsWith("Content-Length")) {
                hasContentLength = true;
            }
            if (header.startsWith("Last-Modified")) {
                hasLastModified = true;
            }
        }
        Assert.assertTrue("Response should contain Content-Length header", hasContentLength);
        Assert.assertTrue("Response should contain Last-Modified header", hasLastModified);
	}

	@Test
	public void directoryListingParentLinkRequest() throws IOException {
		File subDir = tempFolder.newFolder("subdir");
		HttpRequest req = new HttpRequest(new ByteArrayInputStream(("GET " + subDir.getName() + "/ HTTP/1.1\n\n").getBytes()));
		HttpResponse res = new HttpResponse(req, tempFolder.getRoot());
		String body = new String(res.body, StandardCharsets.UTF_8);

		Assert.assertTrue(res.headers.size() > 0);
		Assert.assertEquals(HttpResponse.VERSION + " " + Status._200, res.headers.get(0));
		Assert.assertTrue("Body should contain parent directory link", body.contains("<a href=\"..\">..</a>"));
	}

	@Test
	public void directoryListingNoParentLinkAtRoot() throws IOException {
		HttpRequest req = new HttpRequest(new ByteArrayInputStream("GET / HTTP/1.1\n\n".getBytes()));
		HttpResponse res = new HttpResponse(req, tempFolder.getRoot());
		String body = new String(res.body, StandardCharsets.UTF_8);

		Assert.assertTrue(res.headers.size() > 0);
		Assert.assertEquals(HttpResponse.VERSION + " " + Status._200, res.headers.get(0));
		Assert.assertFalse("Body should not contain parent directory link", body.contains("<a href=\"..\">..</a>"));
	}

	@Test
	public void defaultFileRequest() throws IOException {
		File indexFile = tempFolder.newFile("index.html");
		try (FileWriter writer = new FileWriter(indexFile)) {
			writer.write("<html><body>Hello</body></html>");
		}
		HttpRequest req = new HttpRequest(new ByteArrayInputStream("GET / HTTP/1.1\n\n".getBytes()));
		HttpResponse res = new HttpResponse(req, tempFolder.getRoot());
		String body = new String(res.body, StandardCharsets.UTF_8);

		Assert.assertTrue(res.headers.size() > 0);
		Assert.assertEquals(HttpResponse.VERSION + " " + Status._200, res.headers.get(0));
		Assert.assertEquals("<html><body>Hello</body></html>", body);
	}
}
