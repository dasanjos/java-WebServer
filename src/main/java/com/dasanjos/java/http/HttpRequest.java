package com.dasanjos.java.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * HttpRequest class parses the HTTP Request Line (method, URI, version) and
 * Headers http://www.w3.org/Protocols/rfc2616/rfc2616-sec5.html
 */
public class HttpRequest {

	private static Logger log = Logger.getLogger(HttpRequest.class);

	List<String> headers = new ArrayList<String>();

	Method method;

	String uri;

	String version;

	public HttpRequest(InputStream is) throws IOException {

		try {

			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String str = reader.readLine();
			parseRequestLine(str);

			while (!str.equals("")) {
				str = reader.readLine();
				parseRequestHeader(str);
			}

		}

		catch (Exception e) {

		}
	}

	private void parseRequestLine(String str) {
		try {
			log.info(str);
			String[] split = str.split("\\s+");
			try {
				method = Method.valueOf(split[0]);
			} catch (Exception e) {
				method = Method.UNRECOGNIZED;
			}
			uri = split[1];
			version = split[2];
		} catch (Exception e) {
			//
		}
	}

	private void parseRequestHeader(String str) {
		log.info(str);
		headers.add(str);
	}
}
