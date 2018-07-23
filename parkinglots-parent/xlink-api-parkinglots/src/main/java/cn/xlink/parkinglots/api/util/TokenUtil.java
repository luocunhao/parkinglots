package cn.xlink.parkinglots.api.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;

import java.io.IOException;

/**
 * Created by Hudson_Chi on 2018/3/27
 */

public class TokenUtil {

	public static String VOLIDATEURL = "http://api-test.xlink.io:1080/v2/xlink/valid_token";

	public static JSONObject volidateToken(String token) throws Exception {

		OkHttpClient okHttpClient = new OkHttpClient();

		//使用JSONObject封装参数
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("access-token", token);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Request.Builder builder = new Request.Builder();
		builder.addHeader("Access-Token", "MERERTk1NkU3OTY2ODk5ODY0MENFMDc2NDZBRDc4NDMwOUU1MEU2MkQ1REU5MjkzNjUxMTI0MjBDOTcwMTA2Rg==");
		builder.addHeader("Content-Type", "application/json");
		String      result      = null;
		RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
		Request     request     = builder.post(requestBody).url(TokenUtil.VOLIDATEURL).build();
		try {
			Response response = okHttpClient.newCall(request).execute();
			result = response.body().string();
			response.body().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return JSON.parseObject(result);


	}
}
