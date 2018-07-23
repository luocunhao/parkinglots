package cn.xlink.parkinglots.api.domain;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;


/**进场记录*/
@Data
@Getter
@Setter
@Document(collection="in_parking")
public class InParking implements Serializable{
    @Id
    //事件id
    private String id;
    private String project_id;
//    private String event_id;
    //停车场ID
    private String parking_id;
    //停车场name
    private String parking_name;
    //进场道闸id
    private String gate_id;
    //车牌号码
    private String car_no;
    //车辆品牌
    private String car_brand;
    //品牌型号
    private String car_model;
    //车牌颜色   黄牌0、蓝牌（默认）1、绿色2、白色3、黑色4
    private Integer car_no_color;
    //是否有车牌    0否、1是
    private Integer car_no_card;
    //授权方式 刷卡0，车牌识别1，人工较正3，其它4
    private Integer open_mode;
    //IC卡号
    private String ic_card_info;
    //进场时间,例"2016-01-19T00:00:00Z"
    private String in_time;
    //入场类型  VIP0、固定2、临停3
    private Integer parking_type;
    //固定方案  按天0、月卡1、季度卡2、半年卡3、年卡4、其它5
    private Integer fix_card_value;
    //进场照片
    private String in_photo;
    //进场后剩余车位
    private Integer remain_num;
    //进场后固定卡车位剩余数量
    private Integer remain_fix_num;
    //停车时长类型 超30天 超60天
    private Integer parking_time_type;
    //备注（特殊业务）
    private String in_note;


    //业务数据
    private BarrierGate barrierGate;
    private OutParking outParking;
    //到目前为止已停放时长（区别于出场记录中的停留时长）
    private Long now_parking_time;
    private String start_time;
    private String end_time;
}
