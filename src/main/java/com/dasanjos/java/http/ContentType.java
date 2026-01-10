package com.dasanjos.java.http;

/**
 * ContentType enum uses the file extension to loosely map the available content type based on common media types:
 * http://en.wikipedia.org/wiki/Internet_media_type
 */
public enum ContentType {
    CSS("text/css"),
    GIF("image/gif"),
    HTM("text/html"),
    HTML("text/html"),
    ICO("image/x-icon"),
    JPG("image/jpeg"),
    JPEG("image/jpeg"),
    PNG("image/png"),
    TXT("text/plain"),
    XML("text/xml"),
    PDF("application/pdf"),
    JSON("application/json"),
    JS("application/javascript");

    private final String type;

    ContentType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Content-Type: " + type;
    }
}
