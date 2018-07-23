package cn.xlink.parkinglots.client.exception;

public class ERROR_CODE {
    public static final int BADREQUEST = 400;
    /******************************* 错误码分隔符 ********************************/
    /******************************* 错误码分隔符 ********************************/
    /******************************* 错误码分隔符 ********************************/
    /******************************* 错误码分隔符 ********************************/
    /******************************* 错误码分隔符 ********************************/
    /**
     * HTTP 400 下返回的错误码
     */
    /**
     * 请求数据字段验证不通过
     */
    public static final int PARAM_VALID_ERROR = 4001001;
    /**
	 * 组织ID必填
	 */
	public static final int ORGANIZATION_ID_MUST_INPUT = 40016002;
	/**
	 * 项目ID必填
	 */
	public static final int PROJECT_ID_MUST_INPUT = 40016003;
	/**
	 * 区域ID必填
	 */
	public static final int AREA_ID_MUST_INPUT = 40016004;
	/**
	 * 楼栋ID必填
	 */
	public static final int BUILD_ID_MUST_INPUT = 40016005;
	/**
	 * 单元ID必填
	 */
	public static final int UNIT_ID_MUST_INPUT = 40016006;
	/**
	 * 房间号必填
	 */
	public static final int ROOM_ID_MUST_INPUT = 40016007;
	/**
	 * 名称必填
	 */
	public static final int NAME_MUST_INPUT = 40016008;
	/**
	 * 房间号必填
	 */
	public static final int ROOM_NUM_MUST_INPUT = 40016009;
	/**
	 * 项目管理员必填
	 */
	public static final int ADMIN_ID_MUST_INPUT = 40016010;
	/**
	 * 项目省份必填
	 */
	public static final int PROJECT_PROVINCE_MUST_INPUT = 40016011;
	/**
	 * 项目城市必填
	 */
	public static final int PROJECT_CITY_MUST_INPUT = 40016012;
	/**
	 * 项目详情地址必填
	 */
	public static final int PROJECT_ADDRESS_MUST_INPUT = 40016013;
	/**
	 * 项目经纬度必填
	 */
	public static final int PROJECT_COORDINATE_MUST_INPUT = 40016014;
	
	/**
	 * 项目列表必传
	 */
	public static final int PROJECT_IDS_MUST_INPUT = 40016015;
	/**
	 * 版本必填
	 */
	public static final int VERSION_MUST_INPUT = 40016016;
	/**
	 * 授权类型必填
	 */
	public static final int TYPE_MUST_INPUT = 40016017;
	/**
	 * 授权ID必填
	 */
	public static final int AUTH_IDS_MUST_INPUT = 40016018;
	
	/**
	 * 页码必填
	 */
	public static final int PAGE_NUM_MUST_INPUT = 40016019;
	/**
	 * 每页数据量必填
	 */
	public static final int PAGE_SIZE_MUST_INPUT = 40016020;
    /******************************* 错误码分隔符 ********************************/
    /******************************* 错误码分隔符 ********************************/
    /******************************* 错误码分隔符 ********************************/
    /******************************* 错误码分隔符 ********************************/
    /******************************* 错误码分隔符 ********************************/
    /**
     * HTTP 403 下返回的错误码
     */
    /**
     * 禁止访问
     */
    public static final int INVALID_ACCESS = 4031001;
    /**
     * projectid校验不通过
     * */
    public static final int INVALID_PROJECT = 40316002;
	/**
	 * PRODUCT_ID验证不通过
	 * */
	public static final int INVALID_PRODUCT = 40316003;
    /******************************* 错误码分隔符 ********************************/
    /******************************* 错误码分隔符 ********************************/
    /******************************* 错误码分隔符 ********************************/
    /******************************* 错误码分隔符 ********************************/
    /******************************* 错误码分隔符 ********************************/
    /**
     * HTTP 404 下返回的错误码
     */
    /**
     * URL找不到
     */
    public static final int URL_NOT_FOUND = 4041001;
    

    /**
     * HTTP 503下返回的错误码
     */
    /**
     * 服务端发生异常
     */
    public static final int SERVICE_EXCEPTION = 5031001;
    /**
     * 远程第三方接口调用异常
     * */
    public static final int REMOTE_URL_EXCEPTION = 5031002;
    
    /**
     * 日期格式错误
     */
    public static final int DATE_FORMAT_ERROR = 11002;
    /**
     * 	回调地址未注册
     */
    public static final int CALLBACK_URL_NOT_REGISTER = 12002;
    /**
     * 锁不存在
     * */
    public static final int LOCK_NOT_FOUND = 12001;
    /**
     * 授权关系不存在
     * */
    public static final int AUTHRIZATION_NOT_FOUND = 12004;
    /**
     * 查无数据
     * */
    public static final int QUERY_NO_DATA = 12005;
}