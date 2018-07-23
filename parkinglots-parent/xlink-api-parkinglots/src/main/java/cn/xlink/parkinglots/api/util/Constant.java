package cn.xlink.parkinglots.api.util;

/**
 * Created by Hudson_Chi on 2018/3/27
 */

public class Constant {
	public static String UN_AUTHORIZED = "no token";
	public static String SUCCESS       = "200";
	public static int OK = 200;
	private static String GATE_DEVICE_URI = "/v2/product/{product_id}/device";
	public static String getGateDeviceUri(String parkinglot_id){
		return Constant.GATE_DEVICE_URI.replace("{product_id}",parkinglot_id);
	}
}
