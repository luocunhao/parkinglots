package cn.xlink.parkinglots.client.utils;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.Map.Entry;

public class Http {
//	private static final Logger logger          = LoggerFactory.getLogger(Http.class);
//	private static final int    CONNECT_TIMEOUT = 2000;
//	private static final int    SOCKET_TIMEOUT  = 2000;

	private static final Logger logger          = LoggerFactory.getLogger(Http.class);
	public static final String API_ACCESS_TOKEN_KEY = "Access-Token";
	public static final String ContentTypeKey = "Content-Type";
	public static final String ContentTypeValue  = "application/json";
	private static final int    CONNECT_TIMEOUT = 2000;
	private static final int    SOCKET_TIMEOUT  = 2000;
	public static cn.xlink.parkinglots.client.utils.HttpResponse GetApiWithXlinkToken(String url, String token) throws ClientProtocolException, IOException {
		Map<String, String> header = new HashMap<>();
		header.put(API_ACCESS_TOKEN_KEY, token);
		return Get(url, header);
	}

	public static String getWtihTimeOut(String url, int timeOut) {
		CloseableHttpClient httpclient = PoolHttpConnectionManager.instance().getHttpClient();
		HttpGet             httpGet    = new HttpGet(url);
		httpGet.setConfig(RequestConfig.custom().setSocketTimeout(timeOut).setConnectTimeout(timeOut).build());

		logger.debug("Http get:\r\n" + url);

		String                repsonse     = null;
		CloseableHttpResponse httpResponse = null;
		try {
			httpResponse = httpclient.execute(httpGet);
			if (null != httpResponse && httpResponse.getStatusLine().getStatusCode() == 200) {
				repsonse = EntityUtils.toString(httpResponse.getEntity());
			}
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			if (httpResponse != null) {
				try {
					httpResponse.close();
				} catch (IOException e) {
					logger.error("", e);
				}
			}
		}

		return repsonse;
	}

	public static HttpResponse Get(String url) {
		return Get(url, null, null, CONNECT_TIMEOUT, SOCKET_TIMEOUT);
	}

	public static HttpResponse Get(String url,Map<String,String>headerMap){ return Get(url,headerMap,null,CONNECT_TIMEOUT, SOCKET_TIMEOUT);}

	public static HttpResponse Get(String url, Map<String, String> headerMap, Map<String, String> paramMap, int connect_timeout, int
			socket_timeout) {
		if (paramMap != null && paramMap.isEmpty() == false) {
			StringBuffer param = new StringBuffer();
			int          i     = 0;
			for (String key : paramMap.keySet()) {
				if (i == 0) param.append("?");
				else param.append("&");
				param.append(key).append("=").append(paramMap.get(key));
				i++;
			}
			url += param;
		}

		HttpResponse        resp       = null;
		CloseableHttpClient httpclient = PoolHttpConnectionManager.instance().getHttpClient();
		HttpGet             httpGet    = new HttpGet(url);
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connect_timeout).setSocketTimeout(socket_timeout).build();
		httpGet.setConfig(requestConfig);
		if (headerMap != null && headerMap.isEmpty() == false) {
			for (Entry<String, String> entry : headerMap.entrySet()) {
				httpGet.setHeader(entry.getKey(), entry.getValue());
			}
		}

		logger.debug("Http get:\r\n" + url);
		CloseableHttpResponse httpResponse = null;
		try {
			httpResponse = httpclient.execute(httpGet);
			resp = new HttpResponse(httpResponse.getStatusLine().getStatusCode());
			HeaderIterator headers = httpResponse.headerIterator();
			while (headers.hasNext()) {
				Header header = headers.nextHeader();
				resp.setHeader(header.getName(), header.getValue());
			}
			HttpEntity entity  = httpResponse.getEntity();
			String     content = EntityUtils.toString(entity, "UTF-8");
			resp.setContent(content);

		} catch (Exception e) {
			logger.error("", e);
		} finally {
			if (httpResponse != null) {
				try {
					httpResponse.close();
				} catch (IOException e) {
					logger.error("", e);
				}
			}
		}

		return resp;
	}

	public static HttpResponse GetWithHost(String url, Map<String, String> headerMap, Map<String, String> paramMap, int connect_timeout,
			int socket_timeout, String host) {
		if (paramMap != null && paramMap.isEmpty() == false) {
			StringBuffer param = new StringBuffer();
			int          i     = 0;
			for (String key : paramMap.keySet()) {
				if (i == 0) param.append("?");
				else param.append("&");
				param.append(key).append("=").append(paramMap.get(key));
				i++;
			}
			url += param;
		}

		HttpResponse        resp       = null;
		CloseableHttpClient httpclient = PoolHttpConnectionManager.instance().getHttpClient();
		HttpGet             httpGet    = new HttpGet(url);
		HttpHost            proxyHost  = new HttpHost("47.90.66.7", 9495);
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connect_timeout).setSocketTimeout(socket_timeout).build();
		httpGet.setConfig(requestConfig);
		if (headerMap != null && headerMap.isEmpty() == false) {
			for (Entry<String, String> entry : headerMap.entrySet()) {
				httpGet.setHeader(entry.getKey(), entry.getValue());
			}
		}

		logger.debug("Http get:\r\n" + url);
		CloseableHttpResponse httpResponse = null;
		try {
			httpResponse = httpclient.execute(proxyHost, httpGet);
			resp = new HttpResponse(httpResponse.getStatusLine().getStatusCode());
			HeaderIterator headers = httpResponse.headerIterator();
			while (headers.hasNext()) {
				Header header = headers.nextHeader();
				resp.setHeader(header.getName(), header.getValue());
			}
			HttpEntity entity  = httpResponse.getEntity();
			String     content = EntityUtils.toString(entity, "UTF-8");
			resp.setContent(content);

		} catch (Exception e) {
			logger.error("", e);
		} finally {
			if (httpResponse != null) {
				try {
					httpResponse.close();
				} catch (IOException e) {
					logger.error("", e);
				}
			}
		}

		return resp;
	}

	public static HttpResponse Post(String url, String data) {
		return Post(url, data, null, CONNECT_TIMEOUT, SOCKET_TIMEOUT);
	}

	public static HttpResponse Post(String url, String data, int connect_tiemout, int socket_timeout) {
		return Post(url, data, null, connect_tiemout, socket_timeout);
	}
	public static HttpResponse Post(String url, String data, Map<String, String>headerMap) {
		return Post(url, data,headerMap, CONNECT_TIMEOUT, SOCKET_TIMEOUT);
	}

	public static HttpResponse Post(String url, String data, Map<String, String> headerMap, int connect_timeout, int socket_timeout) {
		HttpResponse        resp       = null;
		CloseableHttpClient httpclient = PoolHttpConnectionManager.instance().getHttpClient();
		HttpPost            httpPost   = new HttpPost(url);
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connect_timeout).setSocketTimeout(socket_timeout).build();
		httpPost.setConfig(requestConfig);
		CloseableHttpResponse httpResponse = null;
		try {
			StringEntity stringEntity = new StringEntity(data, Charset.forName("UTF-8"));
			if (headerMap != null && headerMap.isEmpty() == false) {
				for (Entry<String, String> entry : headerMap.entrySet()) {
					httpPost.setHeader(entry.getKey(), entry.getValue());
				}
			}

			httpPost.setHeader("Connection", "close");
			logger.debug("Http post content:\r\n" + url + "\r\n" + data);

			httpPost.setEntity(stringEntity);
			httpResponse = httpclient.execute(httpPost);
			resp = new HttpResponse(httpResponse.getStatusLine().getStatusCode());
			HeaderIterator headers = httpResponse.headerIterator();
			while (headers.hasNext()) {
				Header header = headers.nextHeader();
				resp.setHeader(header.getName(), header.getValue());
			}
			HttpEntity entity  = httpResponse.getEntity();
			String     content = EntityUtils.toString(entity, "UTF-8");
			resp.setContent(content);
			// httpPost.reset();
			// httpPost.releaseConnection();
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			if (httpResponse != null) {
				try {
					httpResponse.close();
				} catch (IOException e) {
					logger.error("", e);
				}
			}
		}

		return resp;
	}

	public static HttpResponse Put(String url, String data) {
		return Put(url, data, null, CONNECT_TIMEOUT, SOCKET_TIMEOUT);
	}

	public static HttpResponse Put(String url, String data, Map<String, String> headerMap, int connect_timeout, int socket_timeout) {
		HttpResponse        resp       = null;
		CloseableHttpClient httpclient = PoolHttpConnectionManager.instance().getHttpClient();
		HttpPut             httpPut    = new HttpPut(url);
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connect_timeout).setSocketTimeout(socket_timeout).build();
		httpPut.setConfig(requestConfig);

		CloseableHttpResponse httpResponse = null;

		try {
			StringEntity stringEntity = new StringEntity(data, Charset.forName("UTF-8"));
			if (headerMap != null && headerMap.isEmpty() == false) {
				for (Entry<String, String> entry : headerMap.entrySet()) {
					httpPut.setHeader(entry.getKey(), entry.getValue());
				}
			}
			logger.info("Http put content:\r\n" + url + "\r\n" + data);
			httpPut.setEntity(stringEntity);
			httpResponse = httpclient.execute(httpPut);
			resp = new HttpResponse(httpResponse.getStatusLine().getStatusCode());
			HeaderIterator headers = httpResponse.headerIterator();
			while (headers.hasNext()) {
				Header header = headers.nextHeader();
				resp.setHeader(header.getName(), header.getValue());
			}
			HttpEntity entity  = httpResponse.getEntity();
			String     content = EntityUtils.toString(entity, "UTF-8");
			resp.setContent(content);
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			if (httpResponse != null) {
				try {
					httpResponse.close();
				} catch (IOException e) {
					logger.error("", e);
				}
			}
		}

		return resp;
	}

	public static HttpResponse PostWithHeaders(String url, String data, Header[] headers) throws ClientProtocolException, IOException {

		HttpResponse        resp         = null;
		CloseableHttpClient httpclient   = PoolHttpConnectionManager.instance().getHttpClient();
		HttpPost            httpPost     = new HttpPost(url);
		StringEntity        stringEntity = new StringEntity(data, Charset.forName("UTF-8"));

		logger.info("Http post content:\r\n" + url + "\r\n" + data);

		httpPost.setEntity(stringEntity);
		httpPost.setHeaders(headers);
		CloseableHttpResponse httpResponse = httpclient.execute(httpPost);

		try {
			resp = new HttpResponse(httpResponse.getStatusLine().getStatusCode());
			HeaderIterator respHeaders = httpResponse.headerIterator();
			while (respHeaders.hasNext()) {
				Header header = respHeaders.nextHeader();
				resp.setHeader(header.getName(), header.getValue());
			}
			HttpEntity entity  = httpResponse.getEntity();
			String     content = EntityUtils.toString(entity, "UTF-8");
			resp.setContent(content);
		} finally {
			httpResponse.close();
		}
		return resp;
	}

	public static HttpResponse postNamePair(String url, Map<String, String> paramters, Map<String, String> headers, int connect_timeout,
			int socket_timeout) throws Exception {
		HttpResponse resp = null;
		if (Objects.isNull(url)) {
			return resp;
		}

		CloseableHttpClient httpclient = PoolHttpConnectionManager.instance().getHttpClient();
		HttpPost            httpost    = new HttpPost(url);

		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connect_timeout).setSocketTimeout(socket_timeout).build();
		httpost.setConfig(requestConfig);

		if (null != headers && !headers.isEmpty()) {
			Set<String> headerKeys = headers.keySet();
			for (String key : headerKeys) {
				httpost.addHeader(key, headers.get(key));
			}
		}

		if (null != paramters && !paramters.isEmpty()) {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			Set<String>         paramtersKeys  = paramters.keySet();
			for (String key : paramtersKeys) {
				nameValuePairs.add(new BasicNameValuePair(key, paramters.get(key)));
			}
			httpost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
		}

		CloseableHttpResponse httpResponse = httpclient.execute(httpost);
		try {
			resp = new HttpResponse(httpResponse.getStatusLine().getStatusCode());
			HeaderIterator respHeaders = httpResponse.headerIterator();
			while (respHeaders.hasNext()) {
				Header header = respHeaders.nextHeader();
				resp.setHeader(header.getName(), header.getValue());
			}
			HttpEntity entity  = httpResponse.getEntity();
			String     content = EntityUtils.toString(entity, "UTF-8");
			resp.setContent(content);
		} finally {
			httpResponse.close();
		}
		return resp;
	}
}