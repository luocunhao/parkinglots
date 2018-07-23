package cn.xlink.parkinglots.client.filter;


import cn.xlink.parkinglots.api.util.Constant;
import cn.xlink.parkinglots.api.vo.ResultInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Hudson_Chi on 2018/3/27
 */

/**
 * 解决跨域问题和token的校验问题
 */
@Component
public class HttpOptionsRequestFilter implements Filter {

	private static Logger logger = LoggerFactory.getLogger(HttpOptionsRequestFilter.class);

	@Override
	public void init(FilterConfig filterConfig) {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException,
			ServletException {
		HttpServletRequest  httpRequest  = (HttpServletRequest) servletRequest;
		HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

		httpResponse.setHeader("Access-Control-Max-Age", "3600");
		httpResponse.setHeader("Access-Control-Allow-Headers", "token,Origin, X-Requested-With, Content-Type, Accept");
		httpResponse.setHeader("Connection", "close");
		httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		httpResponse.setHeader("Access-Control-Allow-Headers", "Access-Token,Corp-ID,Timestamp");
		httpResponse.setHeader("Access-Control-Allow-Credentials", "true");

		String originUrl = httpRequest.getHeader("Origin");
		String token     = httpRequest.getHeader("Access-Token");
		httpRequest.setAttribute("token", token);

		ResultInfo resultInfo = new ResultInfo();
		boolean    isFilter   = false;

		if (httpRequest.getMethod().equals("OPTIONS")) {
			if (originUrl == null) {
				originUrl = "*";
			}
			httpResponse.setHeader("Access-Control-Allow-Origin", originUrl);
			httpResponse.setStatus(HttpServletResponse.SC_OK);
		} else {
			httpRequest.setAttribute("token", token);
			if (null == token || token.isEmpty()) {
				resultInfo.setCode(Constant.UN_AUTHORIZED);
				resultInfo.setMsg("用户授权认证没有通过!客户端请求参数中无token信息");
			} else {

			}
				/*if (TokenUtil.volidateToken(token)) {
					resultInfo.setCode(Constant.SUCCESS);
					resultInfo.setMsg("用户授权认证通过!");
					isFilter = true;
				} else {
					resultInfo.setCode(Constant.UN_AUTHORIZED);
					resultInfo.setMsg("用户授权认证没有通过!客户端请求参数token信息无效");
				}
			}
			if (resultInfo.getCode() == Constant.UN_AUTHORIZED) {// 验证失败
				PrintWriter        writer = null;
				OutputStreamWriter osw    = null;
				try {
					osw = new OutputStreamWriter(servletResponse.getOutputStream(), "UTF-8");
					writer = new PrintWriter(osw, true);
					String jsonStr = JSON.toJSONString(resultInfo);
					writer.write(jsonStr);
					writer.flush();
					writer.close();
					osw.close();
				} catch (UnsupportedEncodingException e) {
					logger.error("过滤器返回信息失败:" + e.getMessage(), e);
				} catch (IOException e) {
					logger.error("过滤器返回信息失败:" + e.getMessage(), e);
				} finally {
					if (null != writer) {
						writer.close();
					}
					if (null != osw) {
						osw.close();
					}
				}
				return;
			}*/
			if (isFilter) {
				logger.info("token filter过滤ok!");
			}
			filterChain.doFilter(httpRequest, httpResponse);
		}
	}

	@Override
	public void destroy() {

	}

}
