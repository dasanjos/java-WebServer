package com.dasanjos.java.http;

import org.junit.Assert;
import org.junit.Test;

public class ContentTypeTest {

	@Test
	public void getCorrectContentTypeByExtension() {
		Assert.assertEquals("Content-Type: text/css", ContentType.CSS.toString());
		Assert.assertEquals("Content-Type: image/gif", ContentType.GIF.toString());
		Assert.assertEquals("Content-Type: text/html", ContentType.HTM.toString());
		Assert.assertEquals("Content-Type: text/html", ContentType.HTML.toString());
		Assert.assertEquals("Content-Type: image/x-icon", ContentType.ICO.toString());
		Assert.assertEquals("Content-Type: image/jpeg", ContentType.JPG.toString());
		Assert.assertEquals("Content-Type: image/jpeg", ContentType.JPEG.toString());
		Assert.assertEquals("Content-Type: image/png", ContentType.PNG.toString());
		Assert.assertEquals("Content-Type: text/plain", ContentType.TXT.toString());
		Assert.assertEquals("Content-Type: text/xml", ContentType.XML.toString());
		Assert.assertEquals("Content-Type: application/pdf", ContentType.PDF.toString());
		Assert.assertEquals("Content-Type: application/json", ContentType.JSON.toString());
		Assert.assertEquals("Content-Type: application/javascript", ContentType.JS.toString());
	}
}
