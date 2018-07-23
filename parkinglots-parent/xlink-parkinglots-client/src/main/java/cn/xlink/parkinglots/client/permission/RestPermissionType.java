package cn.xlink.parkinglots.client.permission;

/**
 * Restful接口的调用权限
 * <p>
 * <pre>
 * 说明：
 * 	XLINK:云智易内部平台使用，不公开。
 * 	CORP：云智易企业成员使用，一般指在云智易下的企业成员。
 * 	USER：在云智易平台下的企业用户，指广大用户，一般APP开发者使用。
 * 	DEVELOPER：插件应用，在云智易平台下，企业创建的应用。
 * 	USER_APP：插件应用下的用户。
 * 	EMPOWER：授权，由企业在云智易平台创建授权而来。
 * 	ANYBODY：任何人
 * 各种不同类型的权限高低依次应该是：XLINK > EMPOWER > CORP > USER > DEVELOPER > USER_APP > ANYBODY
 *
 * </pre>
 *
 * @author shenweiran(shenweiran @ xlink.cn)
 * @date 2015年10月27日 上午10:29:26
 */
public class RestPermissionType {
    public static final int NONE                 = 0;
    /**
     * 云智易
     */
    public static final int XLINK                = 1;
    /**
     * 企业
     */
    public static final int CORP                 = 2;
    /**
     * 用户
     */
    public static final int USER                 = 4;
    /**
     * 插件应用
     */
    public static final int APP                  = 8;
    /**
     * 插件应用的用户
     */
    @Deprecated
    public static final int USER_APP             = 16;
    /**
     * 授权 （通过Access Key认证而来）
     */
    public static final int EMPOWER              = 32;
    /**
     * 任何人
     */
    public static final int ANYBODY              = 64;
    /**
     * 企业对于应用
     */
    public static final int APP_CORP             = 128;
    /**
     * 用户对于应用
     */
    public static final int APP_USER             = 256;
    /**
     * 设备（ps:终于有了设备端的访问，add by 2016-6-2）
     */
    public static final int DEVICE               = 512;
    /**
     * 经销商
     */
    public static final int DEALER               = 1024;
    /**
     * 大客户
     */
    public static final int HEAVY_BUYER          = 2048;
    /**
     * 设备对于应用
     */
    public static final int APP_DEVICE           = 4096;
    /**
     * 用户对于某应用提供商
     */
    public static final int PROVIDER_USER        = 8192;
    /**
     * 企业成员对于某应用提供商
     */
    public static final int PROVIDER_CORP        = 16384;
    /**
     * 设备对于某应用提供商
     */
    public static final int PROVIDER_DEVICE      = 32768;
    /**
     * 经销商对于某应用提供商
     */
    public static final int PROVIDER_DEALER      = 65536;
    /**
     * 经销商对于应用
     */
    public static final int APP_DEALER           = 131072;
    /**
     * 应用提供商
     */
    public static final int PROVIDER             = 262144;
    /**
     * 大客户对于某应用提供商
     */
    public static final int PROVIDER_HEAVY_BUYER = 524288;
    /**
     * 大客户对于某应用
     */
    public static final int APP_HEAVY_BUYER      = 1048576;

}