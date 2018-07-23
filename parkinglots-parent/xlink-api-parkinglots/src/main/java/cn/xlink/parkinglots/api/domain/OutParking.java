package cn.xlink.parkinglots.api.domain;

import cn.xlink.parkinglots.api.page.PageRequest;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**出场记录*/
@Data
@Getter
@Setter
@Document(collection="out_parking")
public class OutParking implements Serializable{
    @Id
    //事件id
    private String id;
    //事件id
//    private String event_id;
    private String project_id;
    //停车场ID
    private String parking_id;
    //道闸ID
    private String gate_id;
    //进场事件ID
    private String in_parking_id;
    //车牌号码
    private String car_no;
    //开闸方式   刷卡0，车牌识别1，人工较正3，其它4
    private Integer open_mode;
    //IC卡信息
    private String ic_card_info;
    //固定方案  按天0、月卡1、季度卡2、半年卡3、年卡4、其它5
    private Integer fix_card_value;
    //入场时间
    private String in_time;
    //出场时间
    private String out_time;
    //出场照片
    private String out_photo;
    //停留时长   秒
    private Integer parking_time;
    //支付终端
    private String pay_terminal;
    //支付方式   现金0、微信1、支付宝2、其它3　
    private Integer pay_type;
    //收费id 关联固定卡缴费记录
    private String charge_id;
    //应收金额
    private Double ys_money;
    //实收金额 
    private Double ss_money;
    //停车时长类型 超30天 超60天
    private Integer parking_time_type;
    //备注(特殊业务说明)
    private String open_note;


    //业务数据
    private BarrierGate barrierGate;
    private ChargeData chargeData;

    private String start_time;
    private String end_time;
}
