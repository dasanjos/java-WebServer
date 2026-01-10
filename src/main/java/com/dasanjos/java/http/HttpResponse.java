package com.dasanjos.java.http;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


/**
 * HttpResponse class defines the HTTP Response Status Line (method, URI, version) 
 * and Headers http://www.w3.org/Protocols/rfc2616/rfc2616-sec6.html
 */
public class HttpResponse {

	private static final Logger LOGGER = LoggerFactory.getLogger(HttpResponse.class);

	public static final String VERSION = "HTTP/1.0";

	List<String> headers = new ArrayList<String>();

	byte[] body;

	public HttpResponse(HttpRequest req) throws IOException {
		this(req, new File("."));
	}

	public HttpResponse(HttpRequest req, File webRoot) throws IOException {
		if (req == null) {
			return;
		}
		switch (req.method) {
			case GET:
			case HEAD:
				try {
					String rootPath = webRoot.getCanonicalPath();
					File file = new File(webRoot, req.uri);
					String filePath = file.getCanonicalPath();

					if (!filePath.startsWith(rootPath)) {
						LOGGER.warn("Path traversal attempt: {}", req.uri);
						fillHeaders(Status._403);
						fillResponse(Status._403.toString());
					} else if (file.isDirectory()) {
						File indexFile = new File(file, "index.html");
						if (indexFile.exists() && !indexFile.isDirectory()) {
							fillHeaders(Status._200);
							setContentType(indexFile.getName(), headers);
							addFileHeaders(indexFile, headers);
							if (req.method == Method.GET) {
								fillResponse(getBytes(indexFile));
							}
							return;
						}

						fillHeaders(Status._200);
						headers.add(ContentType.HTML.toString());
						StringBuilder result = new StringBuilder("<html><head><title>Index of ");
						result.append(req.uri);
						result.append("</title></head><body><h1>Index of ");
						result.append(req.uri);
						result.append("</h1><hr><pre>");

						if (!req.uri.equals("/")) {
							result.append("<a href=\"..\">..</a>\n");
						}

						File[] files = file.listFiles();
						for (File subfile : files) {
							String fileName = subfile.getName();
							String link = new File(req.uri, fileName).getPath().replace(File.separator, "/");
							result.append(" <a href=\"" + link + "\">" + fileName + "</a>\n");
						}
						result.append("<hr></pre></body></html>");
						if (req.method == Method.GET) {
							fillResponse(result.toString());
						}
						addHtmlHeaders(result.toString(), headers);
					} else if (file.exists()) {
						fillHeaders(Status._200);
						setContentType(req.uri, headers);
						addFileHeaders(file, headers);
						if (req.method == Method.GET) {
							fillResponse(getBytes(file));
						}
					} else {
						LOGGER.info("File not found: {}", req.uri);
						fillHeaders(Status._404);
						fillResponse(Status._404.toString());
					}
				} catch (Exception e) {
					LOGGER.error("Response Error", e);
					fillHeaders(Status._500);
					fillResponse(Status._500.toString());
				}

				break;
			case POST:
				fillHeaders(Status._501);
				fillResponse(Status._501.toString());
				break;
			case UNRECOGNIZED:
				fillHeaders(Status._400);
				fillResponse(Status._400.toString());
				break;
			default:
				fillHeaders(Status._501);
				fillResponse(Status._501.toString());
		}

	}

	private byte[] getBytes(File file) throws IOException {
		int length = (int) file.length();
		byte[] array = new byte[length];
		InputStream in = new FileInputStream(file);
		int offset = 0;
		while (offset < length) {
			int count = in.read(array, offset, (length - offset));
			offset += count;
		}
		in.close();
		return array;
	}

	public void fillHeaders(Status status) {
		headers.add(HttpResponse.VERSION + " " + status.toString());
		headers.add("Connection: close");
		headers.add("Server: SimpleWebServer");
		headers.add("Allow: GET, HEAD");
	}

	private void addFileHeaders(File file, List<String> headers) {
		headers.add("Last-Modified: " + formatDate(new Date(file.lastModified())));
		headers.add("Content-Length: " + file.length());
	}

	private void addHtmlHeaders(String html, List<String> headers) {
		headers.add("Last-Modified: " + formatDate(new Date()));
		headers.add("Content-Length: " + html.length());
	}

	private String formatDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		return sdf.format(date);
	}

	public void fillResponse(String response) {
		body = response.getBytes();
	}

	public void fillResponse(byte[] response) {
		body = response;
	}

	public void write(OutputStream os) throws IOException {
		DataOutputStream output = new DataOutputStream(os);
		for (String header : headers) {
			output.writeBytes(header + "\r\n");
		}
		output.writeBytes("\r\n");
		if (body != null) {
			output.write(body);
		}
		output.writeBytes("\r\n");
		output.flush();
	}

	private void setContentType(String uri, List<String> list) {
		try {
			String ext = uri.substring(uri.indexOf(".") + 1);
			list.add(ContentType.valueOf(ext.toUpperCase()).toString());
		} catch (Exception e) {
			LOGGER.error("ContentType not found", e);
		}
	}
}
