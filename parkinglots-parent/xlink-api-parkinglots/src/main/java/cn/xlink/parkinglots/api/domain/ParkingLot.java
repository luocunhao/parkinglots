package cn.xlink.parkinglots.api.domain;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
/**
 * 停车场集合
 * 注意 实体需要用它的包装类型
 * 例如 int 需要用Integer  
 * */
@Data
@Getter
@Setter
@Document(collection="parking_lot")
public class ParkingLot implements Serializable{
    @Id
    //停车场id
    private String id;

    //项目编号
    private String project_code;
    
    //车场编号
    private String parking_code;

    //车场名称
    private String parking_name;

    //入口道闸数
    private Integer in_park_mount;
    //出口道闸数
    private Integer out_park_mount;
    //车位总数
    private Integer all_book_space;
    //今日收费
    private Double today_charge;
    //今月收费
    private Double month_charge;
    //数据调用凭证
    private String token;  
    //数据分发地址
    private String data_url;
    //登记日期
    private String create_date;
    //道闸产品id 区别道闸id
    private String gate_product_id;
    //道闸名称
    private String gate_name;
    //车场信息-实时车位对象
    private ParkingLotLatestSpace parkingLotLatestSpace;
    /**停车场的统计字段*/
    //项目下停车场数量总数
    private Integer parkingLot_number;
    //项目下道闸数量总数
    private Integer gate_number;
    //项目下停车位总数
    private Integer all_book_space_number;
    //项目下实时空闲车位总数
    private Integer rest_book_space_number;
    //项目下在线道闸总数
    private Integer gate_is_online_number;
    //项目下所有停车场今日收费总数
    private Double today_charge_number;
    /**
     * 停车场收费数据统计
     * */
    //固定卡收费总额
    private Double fix_money;
    //临停卡收费总额
    private Double temporary_money;
    //微信缴费记录数
    private Integer wechat;
    //支付宝缴费记录数
    private Integer alipay;
    //现金支付记录数
    private Integer money;
    //其他支付方式记录数
    private Integer other;
    //临停应收完成率
    private Double payment_completion;


}
