package cn.xlink.parkinglots.client.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter(urlPatterns = "/*")
public class RequestMethodFilter implements Filter {

	private static final String OPTIONS_METHOD                 = "OPTIONS";
    private static final String CONTENT_TYPE                   = "application/json;charset=UTF-8";
    private static final String HEADER_KEY_CONNECTION          = "Connection";
    private static final String HEADER_VALUE_CONNECTION        = "keep-alive";
    private static final String HEADER_KEY_ALLOW_METHODS       = "Access-Control-Allow-Methods";
    private static final String HEADER_VALUE_ALLOW_METHODS     = "GET, POST, PUT, DELETE, OPTIONS";
    private static final String HEADER_KEY_ALLOW_HEADERS       = "Access-Control-Allow-Headers";
    private static final String HEADER_VALUE_ALLOW_HEADERS     = "Access-Token,Corp-ID,Content-Type,Timestamp";
    private static final String HEADER_KEY_ALLOW_CREDENTIALS   = "Access-Control-Allow-Credentials";
    private static final String HEADER_VALUE_ALLOW_CREDENTIALS = "true";
    private static final String HEADER_KEY_ALLOW_ORIGIN        = "Access-Control-Allow-Origin";
    private static final String ALL_ORIGIN                     = "*";
    private static final String ORIGIN                         = "Origin";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		 HttpServletRequest  httpRequest  = (HttpServletRequest) request;
	        HttpServletResponse httpResponse = (HttpServletResponse) response;
	        String              method       = httpRequest.getMethod();
	        httpResponse.setStatus(HttpServletResponse.SC_OK);
	        httpResponse.setContentType(CONTENT_TYPE);
	        String originUrl = httpRequest.getHeader(ORIGIN);
	        if (originUrl == null) {
	            originUrl = ALL_ORIGIN;
	        }
	        httpResponse.setHeader(HEADER_KEY_CONNECTION, HEADER_VALUE_CONNECTION);
	        httpResponse.setHeader(HEADER_KEY_ALLOW_METHODS, HEADER_VALUE_ALLOW_METHODS);
	        httpResponse.setHeader(HEADER_KEY_ALLOW_HEADERS, HEADER_VALUE_ALLOW_HEADERS);
	        httpResponse.setHeader(HEADER_KEY_ALLOW_CREDENTIALS, HEADER_VALUE_ALLOW_CREDENTIALS);
	        httpResponse.setHeader(HEADER_KEY_ALLOW_ORIGIN, originUrl);
	        //httpResponse.addHeader("Transfer-Encoding", "chunked");
	        if (OPTIONS_METHOD.equals(method.toUpperCase())) {
	            httpResponse.getWriter().append("").flush();
	            return;
	        } else {
	            chain.doFilter(request, response);
	        }
	}

	@Override
	public void destroy() {
	}

}
