package cn.xlink.parkinglots.api.domain;

import cn.xlink.parkinglots.api.page.PageRequest;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**车辆登记信息*/
@Data
@Getter
@Setter
@Document(collection="car_info")
public class CarInfo implements Serializable{
    //车辆ID*
    @Id
    private String id;
    //停车场ID
    private String parking_id;
    //	车主姓名
    private String user_name;
    //	身份证号
    private String card_number;
    //	性别  女0、男1
    private Integer user_sex;
    //	手机号码
    private String user_mobile;
    //	车牌号码
    private String car_no;
    //	车辆状态   正常0、黑名单1、白名单2、灰名单3　
    private Integer car_status;
    //	车辆品牌
    private String car_brand;
    //	品牌型号
    private String car_model;
    //	车牌颜色    黄牌0、蓝牌（默认）1、绿色2、白色3、黑色4
    private Integer car_no_color;
    //	是否有车牌   0否、1是
    private Integer car_no_card;
    //	登记日期
    private String record_date;
    /**********/
    //固定卡收费数据对象
    private ChargeData chargeData;
    //最近缴费金额
    private Double recently_delivery_money;
}
