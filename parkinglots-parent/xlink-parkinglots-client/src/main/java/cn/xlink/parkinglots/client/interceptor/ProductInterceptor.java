package cn.xlink.parkinglots.client.interceptor;

import cn.xlink.parkinglots.api.type.URLConfig;
import cn.xlink.parkinglots.client.config.CoreApiConfig;
import cn.xlink.parkinglots.client.exception.ERROR_CODE;
import cn.xlink.parkinglots.client.exception.ProductNotFoundException;
import cn.xlink.parkinglots.client.utils.Http;
import cn.xlink.parkinglots.client.utils.HttpResponse;
//import cn.xlink.smartlockock.api.exception.ERROR_CODE;
//import cn.xlink.smartlock.api.exception.ProductNotFoundException;
//import cn.xlink.smartlock.api.type.URLConfig;
//import cn.xlink.smartlock.client.config.CoreApiConfig;
//import cn.xlink.smartlock.client.utils.Http;
//import cn.xlink.smartlock.client.utils.HttpResponse;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import xlink.core.utils.ContainerGetter;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//@Component
public class ProductInterceptor implements HandlerInterceptor {
    private List<String> requiredList = new ArrayList<>();
    @Autowired
    private CoreApiConfig coreApiConfig;

    public ProductInterceptor() {
//        requiredList.add("/v2/doorlock/lock/{id}");
//        //requiredList.add("/v2/doorlock/state?lock_id={门锁ID}");
//        requiredList.add("/v2/doorlock/card");
//        requiredList.add("/v2/doorlock/card/{id}");
//        requiredList.add("/v2/doorlock/bind/card");
//        requiredList.add("/v2/doorlock/unbind/card");
          requiredList.add("/v2/parks/gate-bacth");
          requiredList.add("/v2/parks/gate");
          requiredList.add("/v2/parks/parkinglots/authorization");
          requiredList.add("/v2/parks/parkinglots");
          requiredList.add("/v2/parks/parkinglots/gates/operations/caroutlot");
          requiredList.add("/v2/parks/parkinglots/gates/operations/carinlot");
          requiredList.add("/v2/parks/projects/parkinglots/gates/operations");
          requiredList.add("/v2/parks/projects/parkinglots/gates/{id}/operations");
          requiredList.add("/v2/parks/parkinglots/{id}");
          requiredList.add("/v2/parks/parkinglots/{parkinglot_id}/gates/{id}/vehicle-out");
          requiredList.add("/v2/parks/parkinglots/{parkinglot_id}/gates/{id}/vehicle-in");
          requiredList.add("/v2/parks/projects/parkinglots/{id}/gates");
          requiredList.add("/v2/parks/projects/parkinglots/{id}/carports");
          requiredList.add("/v2/parks/projects/parkinglots/{id}");


    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String url = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        if (requiredList.contains(url)) {
            return true;
        }
//        return true;

        String body = getBodyString(request);
        JSONObject bodyJson = JSONObject.parseObject(body);
        if (bodyJson != null) {
            String product_id = bodyJson.getString("product_id");
            if (product_id != null) {
                String productUrl = coreApiConfig.getHost()+ URLConfig.PRODUCT_URL.replaceAll("\\{product_id\\}", product_id);
                Map<String, String> headers = ContainerGetter.hashMap();
                headers.put(Http.API_ACCESS_TOKEN_KEY, coreApiConfig.getAccessToken());
                HttpResponse response = Http.Get(productUrl, headers);
                if (response.getResponseCode() == 200) {
                    return true;
                }
            }
        }
        throw new ProductNotFoundException(ERROR_CODE.INVALID_PRODUCT, "INVALID PRODUCT_ID");
        /*
    */
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView
            modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    public static String getBodyString(final ServletRequest request) {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = cloneInputStream(request.getInputStream());
            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    /**
     * Description: 复制输入流</br>
     *
     * @param inputStream
     * @return</br>
     */
    public static InputStream cloneInputStream(ServletInputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buffer)) > -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            byteArrayOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        return byteArrayInputStream;
    }


}
