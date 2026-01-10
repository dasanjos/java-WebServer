package com.dasanjos.java.http;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class StatusTest {

    @Test
    public void testStatusToString() {
        assertEquals("200 OK", Status._200.toString());
        assertEquals("201 Created", Status._201.toString());
        assertEquals("202 Accepted", Status._202.toString());
        assertEquals("204 No Content", Status._204.toString());
        assertEquals("301 Moved Permanently", Status._301.toString());
        assertEquals("302 Moved Temporarily", Status._302.toString());
        assertEquals("304 Not Modified", Status._304.toString());
        assertEquals("400 Bad Request", Status._400.toString());
        assertEquals("401 Unauthorized", Status._401.toString());
        assertEquals("403 Forbidden", Status._403.toString());
        assertEquals("404 Not Found", Status._404.toString());
        assertEquals("500 Internal Server Error", Status._500.toString());
        assertEquals("501 Not Implemented", Status._501.toString());
        assertEquals("502 Bad Gateway", Status._502.toString());
        assertEquals("503 Service Unavailable", Status._503.toString());
    }
}
