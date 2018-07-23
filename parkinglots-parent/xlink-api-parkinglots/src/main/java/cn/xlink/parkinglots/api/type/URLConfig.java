package cn.xlink.parkinglots.api.type;

public class URLConfig {


    /**
     * 修改设备与楼栋 区域关系
     * */
    public static final String DEVICE_BIND_AREA_URL = "/v2/realty-master-data/authorizations/{project_id}/areas/devices";
    /**
     * 新版导入设备
     */
    public static final String DEVICE_IMPORT_BATCH_V2 = "/v2/product/{product_id}/device_import_batch_2";
    /**
     * 批量导入设备
     * */
    public static final String DEVICE_IMPORT_BATCH = "/v2/product/{product_id}/device_import_batch";
    /**
     * 获取设备信息
     */
    public static final String DEVICE_INFO_URL = "/v2/product/{product_id}/device/{device_id}";

    /**
     * 通过mac和pid获取设备ID
     */
    public static final String DEVICE_ID_BY_PID_AND_MAC = "/v2/device_id?mac={mac}&product_id={product_id}";
    /**
     * Access Key管理
     */
    public static final String ACCESSKEY_MANAGER = "/v2/accesskey/{accesskey_id}";
    /**
     * 获取设备列表
     */
    public static final String DEVICE_LIST = "/v2/product/{product_id}/devices";
    /**
     * 获取产品列表
     */
    public static final String PRODUCT_LIST_URL = "/v2/products";
    /**
     * 获取产品详细信息
     */
    public static final String PRODUCT_URL = "/v2/product/{product_id}";
    /**
     * 获取住户户所属房屋
     * */
    public static final String ROOM_LIST_URL = "/v2/realty-master-data/rooms";
    /**
     * 获取住户户所属房屋
     * */
    public static final String USER_ROOM_URL = "/v2/realty-master-data/rooms/{id}";
    /**
     * 获取房屋所属楼栋
     * */
    public static final String ROOM_BUILD_URL = "/v2/realty-master-data/buildings/{id}";
    /**
     *添加业主
     */
    public static final String ADD_OWNER_URL = "/v2/realty-master-data/owners/owner";
    /**
     *查询业主详情
     */
    public static final String USER_INFO_URL = "/v2/realty-master-data/owners/{id}";
    /**
     * 查询业主列表
     * */
    public static final String USER_LIST_URL = "/v2/realty-master-data/owners";
    /**
     * 查询项目详情
     * */
    public static final String PROJECT_INFO_URL = "/v2/realty-master-data/projects/{id}";
    /**
     * 查询区域详情
     * */
    public static final String AREA_INFO_URL = "/v2/realty-master-data/areas/{id}";
    /**
     * 查询项目下的设备列表
     * */
    public static final String PROJECT_DEVICES_URL = "/v2/realty-master-data/authorizations/projects/devices";
    /**
     * 查询地址位置信息
     * */
    public static final String GEOGRAPHY_URL = "/v2/ip/geography";
    /**
     * 获取虚拟设备数据
     * */
    public static final String DEVICE_DATAPOINT_STATUS = "/v2/product/{product_id}/v_device/{device_id}";
    /**
     * 批量获取虚拟设备数据
     * */
    public static final String DEVICES_DATAPOINT_STATUS = "/v2/product/{product_id}/v_devices";
    /**
     * 通过二维码开锁
     * */
    public static final String QRCODE_UNLOCKING="/v2/device/command/{device_id}/datapoint";
}
