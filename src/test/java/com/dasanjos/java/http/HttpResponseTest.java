package com.dasanjos.java.http;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

public class HttpResponseTest {

	@Test
	public void doHeadRequest() throws IOException {
		HttpRequest req = new HttpRequest(new ByteArrayInputStream("HEAD / HTTP/1.1\n\n".getBytes()));
		HttpResponse res = new HttpResponse(req);

		Assert.assertTrue(res.headers.size() > 0);
		Assert.assertEquals(HttpResponse.VERSION + " " + Status._200, res.headers.get(0));
	}

	@Test
	public void doGetRequest() throws IOException {
		HttpRequest req = new HttpRequest(new ByteArrayInputStream("GET / HTTP/1.1\n\n".getBytes()));
		HttpResponse res = new HttpResponse(req);

		Assert.assertTrue(res.headers.size() > 0);
		Assert.assertEquals(HttpResponse.VERSION + " " + Status._200, res.headers.get(0));
	}

	@Test
	public void unknownRequest() throws IOException {
		HttpRequest req = new HttpRequest(new ByteArrayInputStream("WHAT / HTTP/1.1\n\n".getBytes()));
		HttpResponse res = new HttpResponse(req);

		Assert.assertTrue(res.headers.size() > 0);
		Assert.assertEquals(HttpResponse.VERSION + " " + Status._400, res.headers.get(0));
	}
}
