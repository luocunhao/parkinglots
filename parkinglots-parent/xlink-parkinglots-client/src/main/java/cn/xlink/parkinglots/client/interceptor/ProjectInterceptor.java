package cn.xlink.parkinglots.client.interceptor;

import cn.xlink.parkinglots.api.type.URLConfig;
import cn.xlink.parkinglots.client.config.CoreApiConfig;
import cn.xlink.parkinglots.client.exception.ERROR_CODE;
import cn.xlink.parkinglots.client.exception.ProjectNotFoundException;
import cn.xlink.parkinglots.client.exception.custom.Rest503StatusException;
import cn.xlink.parkinglots.client.permission.AccessToken;
import cn.xlink.parkinglots.client.utils.Http;
import cn.xlink.parkinglots.client.utils.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import xlink.core.utils.ContainerGetter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

//import cn.xlink.smartlock.api.exception.ERROR_CODE;
//import cn.xlink.smartlock.api.exception.ProjectNotFoundException;
//import cn.xlink.smartlock.api.exception.Rest503StatusException;
//import cn.xlink.smartlock.api.type.URLConfig;
//import cn.xlink.smartlock.client.config.CoreApiConfig;
//import cn.xlink.smartlock.client.permission.AccessToken;
//import cn.xlink.smartlock.client.utils.Http;
//import cn.xlink.smartlock.client.utils.HttpResponse;

//@Component
public class ProjectInterceptor implements HandlerInterceptor{
    @Autowired
    private CoreApiConfig coreApiConfig;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o) throws Exception {
        Map<String, Object> mapApp = (Map<String, Object>)request.getAttribute("org.springframework.web.servlet.HandlerMapping.uriTemplateVariables");
        String project_id = (String)mapApp.get("project_id");
        AccessToken accessToken = (AccessToken) request.getAttribute("token");
        String token = accessToken.getAccessToken();
        String url = coreApiConfig.getProjectHost()+ URLConfig.PROJECT_INFO_URL.replaceAll("\\{id\\}",project_id);
        Map<String,String> headers = ContainerGetter.hashMap();
        headers.put(Http.API_ACCESS_TOKEN_KEY, token);
        try {
            HttpResponse response = Http.Get(url,headers);
            if(response.getResponseCode()==200){
                return true;
            }
        } catch (Exception e) {
           throw new Rest503StatusException(ERROR_CODE.REMOTE_URL_EXCEPTION,"PROJECT_INFO_URL ERROR");
        }
        throw new ProjectNotFoundException(ERROR_CODE.INVALID_PROJECT,"PROJECT INVALID");
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
