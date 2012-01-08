package com.dasanjos.java.http;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

public class HttpRequestTest {

	@Test
	public void doHeadRequest() throws IOException {
		HttpRequest req = new HttpRequest(new ByteArrayInputStream("HEAD / HTTP/1.1\n\n".getBytes()));
		Assert.assertEquals(Method.HEAD, req.method);
	}

	@Test
	public void doGetRequest() throws IOException {
		HttpRequest req = new HttpRequest(new ByteArrayInputStream("GET / HTTP/1.1\n\n".getBytes()));
		Assert.assertEquals(Method.GET, req.method);
	}

	@Test
	public void unknownRequest() throws IOException {
		HttpRequest req = new HttpRequest(new ByteArrayInputStream("WHAT / HTTP/1.1\n\n".getBytes()));
		Assert.assertEquals(Method.UNRECOGNIZED, req.method);
	}
}
