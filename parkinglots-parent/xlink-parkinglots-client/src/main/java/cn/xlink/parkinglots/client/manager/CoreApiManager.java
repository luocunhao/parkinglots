package cn.xlink.parkinglots.client.manager;


import cn.xlink.parkinglots.client.config.CoreApiConfig;
import cn.xlink.parkinglots.client.permission.AccessToken;
import cn.xlink.parkinglots.client.utils.Http;
import cn.xlink.parkinglots.client.utils.HttpResponse;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CoreApiManager {
	@Autowired
	private CoreApiConfig coreApiConfig;

	private static final String HEADER_ACCESS_TOKEN = "Access-Token";
	private static final String BODY_ACCESS_TOKEN   = "access_token";

	/**
	 * api认证
	 *
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	public AccessToken authApi(String accessToken) throws Exception {
		JSONObject bodyJson = new JSONObject();
		bodyJson.put(BODY_ACCESS_TOKEN, accessToken);
		String              data      = bodyJson.toJSONString();
		String              validUrl  = coreApiConfig.getHost() + CoreApiConfig.ACCESS_TOKEN_VALID_URL;
		Map<String, String> headerMap = new HashMap<>();
		headerMap.put(HEADER_ACCESS_TOKEN, coreApiConfig.getAccessToken());
		HttpResponse authResponse = Http.Post(validUrl, data, headerMap, 10000, 10000);
		JSONObject   authJson     = JSONObject.parseObject(authResponse.getContent());
		String       dataString   = authJson.toJSONString();
		return AccessToken.from(accessToken, dataString);
	}
}
