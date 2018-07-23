package cn.xlink.parkinglots.client.utils;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http连接池管理
 */
public class PoolHttpConnectionManager {

	private static final Logger                    logger    = LoggerFactory.getLogger(PoolHttpConnectionManager.class);
	private static final PoolHttpConnectionManager singleton = new PoolHttpConnectionManager();


	private final CloseableHttpClient httpClient;
	private final PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();

	public static final PoolHttpConnectionManager instance() {
		return singleton;
	}

	public void init(int max, int per) {
		cm.setMaxTotal(max);
		cm.setDefaultMaxPerRoute(per);
	}

	private PoolHttpConnectionManager() {
		httpClient = HttpClients.custom().setConnectionManager(cm).build();
	}

	public CloseableHttpClient getHttpClient() {
		return httpClient;
	}


}

//	private static       PoolingHttpClientConnectionManager clientConnectionManager = null;
//	private static       CloseableHttpClient                httpClient              = null;
//	private static       RequestConfig                      config                  = RequestConfig.custom().setCookieSpec(CookieSpecs
//			.STANDARD_STRICT).build();
//	private final static Object                             syncLock                = new Object();
//
//	/**
//	 * 创建httpclient连接池并初始化
//	 */
//	@PostConstruct
//	private void init() {
//		try {
//			//添加对https的支持，该sslContext没有加载客户端证书
//			// 如果需要加载客户端证书，请使用如下sslContext,其中KEYSTORE_FILE和KEYSTORE_PASSWORD分别是你的证书路径和证书密码
//			//KeyStore keyStore  =  KeyStore.getInstance(KeyStore.getDefaultType()
//			//FileInputStream instream =   new FileInputStream(new File(KEYSTORE_FILE));
//			//keyStore.load(instream, KEYSTORE_PASSWORD.toCharArray());
//			//SSLContext sslContext = SSLContexts.custom().loadKeyMaterial(keyStore,KEYSTORE_PASSWORD.toCharArray())
//			// .loadTrustMaterial(null, new TrustSelfSignedStrategy())
//			//.build();
//			SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();
//			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory
//					.getDefaultHostnameVerifier());
//			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create().register("https",
//					sslsf).register("http", PlainConnectionSocketFactory.getSocketFactory()).build();
//			clientConnectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
//			clientConnectionManager.setMaxTotal(50);
//			clientConnectionManager.setDefaultMaxPerRoute(25);
//		} catch (Exception e) {
//			logger.warn("httpUtils init get exception:", e);
//		}
//	}
//
//	public static CloseableHttpClient getHttpClient() {
//		if (httpClient == null) {
//			synchronized (syncLock) {
//				if (httpClient == null) {
//					CookieStore       cookieStore = new BasicCookieStore();
//					BasicClientCookie cookie      = new BasicClientCookie("sessionID", "######");
//					cookie.setDomain("#####");
//					cookie.setPath("/");
//					cookieStore.addCookie(cookie);
//					httpClient = HttpClients.custom().setConnectionManager(clientConnectionManager).setDefaultCookieStore(cookieStore)
//							.setDefaultRequestConfig(config).build();
//				}
//			}
//		}
//		return httpClient;
//	}
//
//	/**
//	 * get请求
//	 *
//	 * @param url
//	 * @param headers
//	 * @return
//	 */
//	public static HttpEntity httpGet(String url, Map<String, Object> headers) {
//		CloseableHttpClient httpClient = getHttpClient();
//		HttpRequest         httpGet    = new HttpGet(url);
//		if (headers != null && !headers.isEmpty()) {
//			httpGet = setHeaders(headers, httpGet);
//		}
//		CloseableHttpResponse response = null;
//		try {
//			response = httpClient.execute((HttpGet) httpGet);
//			HttpEntity entity = response.getEntity();
//			return entity;
//		} catch (Exception e) {
//			e.printStackTrace();
//
//		}
//		return null;
//	}
//
//	/**
//	 * post请求,使用json格式传参
//	 *
//	 * @param url
//	 * @param headers
//	 * @param data
//	 * @return
//	 */
//	public static HttpEntity httpPost(String url, Map<String, Object> headers, String data) {
//		CloseableHttpClient httpClient = getHttpClient();
//		HttpRequest         request    = new HttpPost(url);
//		if (headers != null && !headers.isEmpty()) {
//			request = setHeaders(headers, request);
//		}
//		CloseableHttpResponse response = null;
//
//		try {
//			HttpPost httpPost = (HttpPost) request;
//			httpPost.setEntity(new StringEntity(data, ContentType.create("application/json", "UTF-8")));
//			response = httpClient.execute(httpPost);
//			HttpEntity entity = response.getEntity();
//			return entity;
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	/**
//	 * 使用表单键值对传参
//	 */
//	public static HttpEntity PostForm(String url, Map<String, Object> headers, List<NameValuePair> data) {
//		CloseableHttpClient httpClient = getHttpClient();
//		HttpRequest         request    = new HttpPost(url);
//		if (headers != null && !headers.isEmpty()) {
//			request = setHeaders(headers, request);
//		}
//		CloseableHttpResponse response = null;
//		UrlEncodedFormEntity  uefEntity;
//		try {
//			HttpPost httpPost = (HttpPost) request;
//			uefEntity = new UrlEncodedFormEntity(data, "UTF-8");
//			httpPost.setEntity(uefEntity);
//			// httpPost.setEntity(new StringEntity(data, ContentType.create("application/json", "UTF-8")));
//			response = httpClient.execute(httpPost);
//			HttpEntity entity = response.getEntity();
//			return entity;
//		} catch (IOException e) {
//			e.printStackTrace();
//
//		}
//		return null;
//	}
//
//	/**
//	 * 设置请求头信息
//	 *
//	 * @param headers
//	 * @param request
//	 * @return
//	 */
//	private static HttpRequest setHeaders(Map<String, Object> headers, HttpRequest request) {
//		for (Map.Entry entry : headers.entrySet()) {
//			if (!entry.getKey().equals("Cookie")) {
//				request.addHeader((String) entry.getKey(), (String) entry.getValue());
//			} else {
//				Map<String, Object> Cookies = (Map<String, Object>) entry.getValue();
//				for (Map.Entry entry1 : Cookies.entrySet()) {
//					request.addHeader(new BasicHeader("Cookie", (String) entry1.getValue()));
//				}
//			}
//		}
//		return request;
//	}
//
//	public static Map<String, String> getCookie(String url) {
//		CloseableHttpClient   httpClient = getHttpClient();
//		HttpRequest           httpGet    = new HttpGet(url);
//		CloseableHttpResponse response   = null;
//		try {
//			response = httpClient.execute((HttpGet) httpGet);
//			Header[]            headers = response.getAllHeaders();
//			Map<String, String> cookies = new HashMap<String, String>();
//			for (Header header : headers) {
//				cookies.put(header.getName(), header.getValue());
//			}
//			return cookies;
//		} catch (Exception e) {
//			e.printStackTrace();
//
//		}
//		return null;
//	}


