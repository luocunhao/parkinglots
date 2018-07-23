package cn.xlink.parkinglots.api.response;

import cn.xlink.parkinglots.api.common.DateUtils;
import cn.xlink.parkinglots.api.exception.ErrorCode;

public class ResponseUtil {

	private static final Integer RESPONSE_STATUS_OK = 200;
    private static final Integer RESPONSE_STATUS_NOK = 500;
    private static final String RESPONSE_CODE_SUCCESS = "000000";
    private static final String RESPONSE_MESSAGE_SUCCESS = "请求成功";

    /**
     * 业务错误
     * @param errorCode 错误码，不能为null
     * @return 表示业务错误的响应结果
     */
    public static Response<Void> bizError(ErrorCode errorCode) {
        return bizError(errorCode.getCode(), errorCode.getDesc(), null);
    }
    
    /**
     * 业务错误
     * @param code 错误码
     * @param desc 错误信息
     * @return 表示业务错误的响应结果
     */
    public static Response<Void> bizError(String code, String desc) {
        return bizError(code, desc, null);
    }
    
    /**
     * 业务错误
     * @param errorCode 错误码，不能为null
     * @param data 相关业务数据，作为错误信息的业务扩展数据
     * @return 表示业务错误的响应结果
     */
    public static <T> Response<T> bizError(ErrorCode errorCode, T data) {
        return bizError(errorCode.getCode(), errorCode.getDesc(), data);
    }
    
    /**
     * @param code 错误码
     * @param desc 错误信息
     * @param data 相关业务数据，作为错误信息的业务扩展数据
     * @return 表示业务错误的响应结果
     */
    public static <T> Response<T> bizError(String code, String desc, T data) {
    	Response<T> response = new Response<T>();
        response.setCode(code);
        response.setMsg(desc);
        response.setResponseTime(DateUtils.getCurrentDate());
        response.setStatus(RESPONSE_STATUS_OK);
        response.setData(data);
        return response;
    }
    
    /**
     * 成功数据（无返回）
     * @return 成功数据（无返回）
     */
    public static Response<Void> success() {
        Response<Void> response = setCommonResponseSuccessProperties();
        return response;
    }

    /**
     * 成功数据（非列表）
     * @param data 数据
     * @return 成功数据（非列表）
     */
    public static <T> Response<T> success(T data) {
        Response<T> response = setCommonResponseSuccessProperties();
        response.setData(data);
        return response;
    }


//    public static <T> Response<T> successCount(T data,long count) {
//        Response<T> response = setCommonResponseSuccessProperties();
//        response.setData(data);
//        response.setCount(count);
//        return response;
//    }

    private static <T> Response<T> setCommonResponseSuccessProperties() {
        Response<T> response = new Response<>();
        response.setMsg(RESPONSE_MESSAGE_SUCCESS);
        response.setCode(RESPONSE_CODE_SUCCESS);
        response.setStatus(RESPONSE_STATUS_OK);
        response.setResponseTime(DateUtils.getCurrentDate());
        return response;
    }
	public static void main(String[] args) {
		

	}

}
