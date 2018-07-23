package cn.xlink.parkinglots.client.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
	private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

	private int code;
	private Map<String, String> headers = new HashMap<String, String>();
	private String content;

	public HttpResponse(int code) {
		this.code = code;
	}

	public int getResponseCode() {
		return this.code;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;

		logger.debug("Http response content:\r\n" + content);
	}

	public void setHeader(String name, String value) {
		headers.put(name, value);
	}

	public String getHeader(String name) {
		return headers.get(name);
	}

	@Override
	public String toString() {
		return "HttpResponse [code=" + code + ", headers=" + headers + ", content=" + content + "]";
	}
}
