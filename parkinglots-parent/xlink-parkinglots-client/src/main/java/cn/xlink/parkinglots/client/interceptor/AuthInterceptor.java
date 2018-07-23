package cn.xlink.parkinglots.client.interceptor;


import cn.xlink.parkinglots.client.exception.ERROR_CODE;
import cn.xlink.parkinglots.client.exception.Rest403StatusException;
import cn.xlink.parkinglots.client.manager.CoreApiManager;
import cn.xlink.parkinglots.client.permission.AccessToken;
import cn.xlink.parkinglots.client.permission.RestPermissionType;
//import cn.xlink.smartlock.api.exception.ERROR_CODE;
////import cn.xlink.smartlock.api.exception.Rest403StatusException;
////import cn.xlink.smartlock.client.Manager.CoreApiManager;
////import cn.xlink.smartlock.client.permission.AccessToken;
////import cn.xlink.smartlock.client.permission.RestPermissionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class AuthInterceptor implements HandlerInterceptor {

    private Map<String, Integer> requiredAuthMap;

    @Autowired
    private CoreApiManager coreApiManager;

    public AuthInterceptor() {
        requiredAuthMap = new ConcurrentHashMap<>();
		/**
         * 门锁接口
         * */
//		requiredAuthMap.put("/v2/doorlock/operator/set/pwd", RestPermissionType.CORP | RestPermissionType.EMPOWER);
//        requiredAuthMap.put("/v2/doorlock/operator/unset/pwd",RestPermissionType.CORP | RestPermissionType.EMPOWER);
//        requiredAuthMap.put("/v2/doorlock/operator/get/pwd-list",RestPermissionType.CORP | RestPermissionType.EMPOWER);
//        requiredAuthMap.put("/v2/doorlock/operator/set/card",RestPermissionType.CORP | RestPermissionType.EMPOWER);
//        requiredAuthMap.put("/v2/doorlock/operator/unset/card",RestPermissionType.CORP | RestPermissionType.EMPOWER);
//        requiredAuthMap.put("/v2/doorlock/operator/get/card-list",RestPermissionType.CORP | RestPermissionType.EMPOWER);
//        requiredAuthMap.put("/v2/doorlock/state",RestPermissionType.CORP | RestPermissionType.EMPOWER);
//        requiredAuthMap.put("/v2/doorlock/state-list",RestPermissionType.CORP | RestPermissionType.EMPOWER);
//        requiredAuthMap.put("/v2/doorlock/region", RestPermissionType.CORP | RestPermissionType.EMPOWER);
//        requiredAuthMap.put("/v2/doorlock/lock", RestPermissionType.CORP | RestPermissionType.EMPOWER);
//        requiredAuthMap.put("/v2/doorlock/lock/batch", RestPermissionType.CORP | RestPermissionType.EMPOWER);
//        requiredAuthMap.put("/v2/doorlock/qrcode/unlock",RestPermissionType.CORP|RestPermissionType.EMPOWER);
//        requiredAuthMap.put("/v2/doorlock/card",RestPermissionType.CORP|RestPermissionType.EMPOWER);
//        requiredAuthMap.put("/v2/doorlock/card/{id}",RestPermissionType.CORP|RestPermissionType.EMPOWER);
//        requiredAuthMap.put("/v2/doorlock/bind/card",RestPermissionType.CORP|RestPermissionType.EMPOWER);
//        requiredAuthMap.put("/v2/doorlock/unbind/card",RestPermissionType.CORP|RestPermissionType.EMPOWER);
//        requiredAuthMap.put("/v2/doorlock/qrcode",RestPermissionType.CORP|RestPermissionType.EMPOWER);
//        requiredAuthMap.put("/v2/doorlock/qrcode/unset",RestPermissionType.CORP|RestPermissionType.EMPOWER);
//          requiredAuthMap.put("/v2/parks/parkinglots/gates/operations/caroutlot",RestPermissionType.CORP|RestPermissionType.EMPOWER);
//		/**
//		 * 项目权限
//		 */
//		requiredAuthMap.put("/v2/smart-community/authorizations/projects/{project_id}", RestPermissionType.CORP | RestPermissionType.EMPOWER);
//		requiredAuthMap.put("/v2/smart-community/authorizations/projects/{project_id}/{type}/{id}", RestPermissionType.CORP | RestPermissionType.EMPOWER);
//		requiredAuthMap.put("/v2/smart-community/authorizations/projects/project_id", RestPermissionType.CORP | RestPermissionType.EMPOWER);
//		requiredAuthMap.put("/v2/smart-community/authorizations/projects", RestPermissionType.CORP | RestPermissionType.EMPOWER);
//
//		/**
//		 * 产品授权
//		 */
//		requiredAuthMap.put("/v2/smart-community/authorizations/projects/{project_id}/products", RestPermissionType.CORP | RestPermissionType.EMPOWER);
//		requiredAuthMap.put("/v2/smart-community/authorizations/projects/{project_id}/products", RestPermissionType.CORP | RestPermissionType.EMPOWER);
//		requiredAuthMap.put("/v2/smart-community/authorizations/projects/{project_id}/products/{product_id}", RestPermissionType.CORP | RestPermissionType.EMPOWER);
//
//		requiredAuthMap.put("/v2/smart-community/household", RestPermissionType.CORP | RestPermissionType.EMPOWER);
//		requiredAuthMap.put("/v2/smart-community/household/{id}", RestPermissionType.CORP | RestPermissionType.EMPOWER);
//		requiredAuthMap.put("/v2/smart-community/households", RestPermissionType.CORP | RestPermissionType.EMPOWER);
		
		
		requiredAuthMap.put("/v2/parks/parkinglots/{id}", RestPermissionType.CORP | RestPermissionType.EMPOWER);
		requiredAuthMap.put("/v2/parks/parkinglots/save", RestPermissionType.CORP | RestPermissionType.EMPOWER);
		requiredAuthMap.put("/v2/parks/parkinglots/list", RestPermissionType.CORP | RestPermissionType.EMPOWER);
		requiredAuthMap.put("/v2/parks/parkinglots/page", RestPermissionType.CORP | RestPermissionType.EMPOWER);

		requiredAuthMap.put("/v2/parks/parkinglots/{project_id}/save",RestPermissionType.CORP | RestPermissionType.EMPOWER);
        requiredAuthMap.put("/v2/parks/parkinglots/{project_id}/msg",RestPermissionType.CORP | RestPermissionType.EMPOWER);
        requiredAuthMap.put("/v2/parks/parkinglots/{parking_id}/delete",RestPermissionType.CORP | RestPermissionType.EMPOWER);


        requiredAuthMap.put("/v2/parks/parkinglots/gate/{parking_id}/all_list",RestPermissionType.CORP | RestPermissionType.EMPOWER);
        requiredAuthMap.put("/v2/parks/parkinglots/gate/{gate_id}/operations",RestPermissionType.CORP | RestPermissionType.EMPOWER);
        requiredAuthMap.put("/v2/parks/parkinglots/gate/authorization/save",RestPermissionType.CORP | RestPermissionType.EMPOWER);
        requiredAuthMap.put("/v2/parks/parkinglots/gate/{parking_id}/list",RestPermissionType.CORP | RestPermissionType.EMPOWER);
        requiredAuthMap.put("/v2/parks/parkinglots/gate/open_info/{project_id}/list",RestPermissionType.CORP | RestPermissionType.EMPOWER);

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        if (!requiredAuthMap.containsKey(url)) {
            return true;
        }

        int permission = requiredAuthMap.get(url);
        if ((permission & RestPermissionType.ANYBODY) > 0) {
            return true;
        }

        String accessToken = request.getHeader("Access-Token");
        if (StringUtils.isEmpty(accessToken)) {
            throw new Rest403StatusException(ERROR_CODE.INVALID_ACCESS, "Access Invalid");
        }

        AccessToken token           = coreApiManager.authApi(accessToken);
        int         tokenPermission = token.getPermission();
        request.setAttribute("token", token);
        if ((tokenPermission & permission) > 0) {
            if ((tokenPermission & RestPermissionType.CORP) > 0) {
            }
            if ((tokenPermission & RestPermissionType.EMPOWER) > 0) {
            }
            if ((tokenPermission & RestPermissionType.USER) > 0) {
            }
            if ((tokenPermission & RestPermissionType.XLINK) > 0) {
            }
            return true;
        }

        throw new Rest403StatusException(ERROR_CODE.INVALID_ACCESS, "Access Invalid");
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws
            Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}