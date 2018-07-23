package cn.xlink.parkinglots.api.domain;

import cn.xlink.parkinglots.api.page.PageRequest;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**固定卡收费数据*/
@Data
@Getter
@Setter
@Document(collection="charge_data")
public class ChargeData implements Serializable{
    //	收费ID*
    @Id
    private String id;
    //项目id
    private String project_id;
    //	停车场ID
    private String parking_id;
    //	车辆ID
    private String car_id;
    //车牌号
    private String car_no;
    // 	缴费日期
    private String charge_date;
    //	固定方案   按天0、月卡1、季度卡2、半年卡3、年卡4、其它5
    private Integer fix_card_value;
    //	包含月份数
    private Integer month_num;
    //	计费周期-开始日期
    private String billcharge_start;
    //	计费周期-到期日期
    private String billcharge_end;
    //	应收金额 
    private Double ys_money;
    //	实收金额
    private Double ss_money;
    //	支付终端
    private String pay_terminal;
    //	支付方式   现金0、微信1、支付宝2、其它3
    private Integer pay_type;

    /**业务数据*/
    //固定卡月卡总数
    private Integer ic_month;
    //固定卡季度卡总数
    private Integer ic_quarter;
    //固定卡半年卡总数
    private Integer ic_half_year;
    //固定卡年卡总数
    private Integer ic_year;
    //登记车辆总数
    private Integer register_car;
    //上个月固定卡收入
    private Double last_month_money;
    //本月预计收入
    private Double this_month_money;

    //最近一年支付宝支付总数
    private Integer alipay_year;
    //最近一年现金支付总数
    private Integer cash_year;
    //最近一年微信支付总数
    private Integer wechat_year;
    //最近一年其它支付总数
    private Integer other_year;

    //正在使用的按天算的卡总数
    private Integer ic_day_sum;
    //正在使用的月卡总数
    private Integer ic_month_sum;
    //正在使用的季度卡总数
    private Integer ic_quarter_sum;
    //正在使用的半年卡总数
    private Integer ic_half_year_sum;
    //正在使用的年卡总数
    private Integer ic_year_sum;
    //正在使用的其他卡总数
    private Integer ic_other_sum;
}
