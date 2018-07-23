package cn.xlink.parkinglots.client.exception.custom;


import com.alibaba.fastjson.JSONObject;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;

public abstract class RestStatusException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = -8672946041107197737L;

	protected final int    errorcode;
	protected final String msg;
	protected       String callback;


	public RestStatusException(int errorcode, String msg) {
		this.errorcode = errorcode;
		this.msg = msg;
	}

	public RestStatusException(int errorcode, String msg, Exception e) {
		super();
		this.errorcode = errorcode;
		this.msg = msg;
	}


	public void setCallback(String callback) {
		this.callback = callback;
	}

	public abstract HttpResponseStatus getHttpStatus();

	@Override
	public String getMessage() {
		JSONObject root       = new JSONObject();
		JSONObject error_json = new JSONObject();
		error_json.put("code", errorcode);
		error_json.put("msg", msg);
		root.put("error", error_json);
		String ret = root.toJSONString();
		if (callback != null) {
			return String.format("%s(%s)", callback, ret);
		}
		return ret;
	}

	public int getErrorcode() {
		return errorcode;
	}

	public String getMsg() {
		return msg;
	}

}
