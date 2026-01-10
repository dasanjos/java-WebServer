package com.dasanjos.java.http;

/**
 * Status enum maps the HTTP/1.1 available response status codes 
 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec6.html
 */
public enum Status {
	_200("200 OK"),
	_201("201 Created"),
	_202("202 Accepted"),
	_204("204 No Content"),
	_301("301 Moved Permanently"),
	_302("302 Moved Temporarily"),
	_304("304 Not Modified"),
	_400("400 Bad Request"),
	_401("401 Unauthorized"),
	_403("403 Forbidden"),
	_404("404 Not Found"),
	_500("500 Internal Server Error"),
	_501("501 Not Implemented"),
	_502("502 Bad Gateway"),
	_503("503 Service Unavailable");

	private final String status;

	Status(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return status;
	}
}
