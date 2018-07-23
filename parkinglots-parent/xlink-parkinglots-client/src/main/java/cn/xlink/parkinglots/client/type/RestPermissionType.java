package cn.xlink.parkinglots.client.type;

/**
 * Restful接口的调用权限
 * <p>
 * <pre>
 * 说明：
 * 	CORP：云智易企业成员使用，一般指在云智易下的企业成员。
 * 	USER：在云智易平台下的企业用户，指广大用户，一般APP开发者使用。
 * 	EMPOWER：授权，由企业在云智易平台创建授权而来。
 * 	ANYBODY：任何人
 */
public class RestPermissionType {
	/**
	 * 云智易
	 */
	public static final int XLINK   = 1;
	/**
	 * 企业
	 */
	public static final int CORP    = 2;
	/**
	 * 用户
	 */
	public static final int USER    = 4;
	/**
	 * 授权 （通过Access Key认证而来）
	 */
	public static final int EMPOWER = 32;
	/**
	 * 任何人
	 */
	public static final int ANYBODY = 64;

}
