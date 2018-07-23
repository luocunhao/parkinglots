package cn.xlink.parkinglots.api.domain;

import cn.xlink.parkinglots.api.page.PageRequest;
import cn.xlink.parkinglots.api.page.PageResult;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**异常开闸记录*/
@Data
@Getter
@Setter
@Document(collection="abnormal_open_info")
public class AbnormalOpenInfo implements Serializable {
    //	事件ID*
    @Id
    private String id;
    //	停车场ID
    private String parking_id;
    //	道闸ID
    private String gate_id;
    //	异常开闸类型
    private String abnormal_open_type;
    //	开闸时间
    private String open_date;
    //	原因
    private String open_reason;

    /**开闸原因统计字段*/
    //一个月内无牌车数量
    private Integer unlicensed_count_month;
    //一个月内其他故障数量
    private Integer other_count_month;
    //一个月内硬件故障数量
    private Integer hardware_count_month;

}
