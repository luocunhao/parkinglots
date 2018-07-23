package cn.xlink.parkinglots.api.domain;

import cn.xlink.parkinglots.api.page.PageRequest;
import com.sun.org.apache.bcel.internal.generic.DLOAD;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

/**车场信息-实时车位*/
@Data
@Getter
@Setter
@Document(collection="parking_lot_latest_space")
public class ParkingLotLatestSpace implements Serializable {
    //实时信息的实体自动生成_id
    @Id
    private String id;
    //	停车场ID
    private String parking_id;
    //	当前空闲数
    private Integer rest_book_space;
    //	固定车位总数
    private Integer regular_book_space;
    //	当前固定空闲数
    private Integer rest_regular_book_space;

    /*业务数据*/
    //最近半年固定卡停车位空闲停车位分布
    //最近半年本月固定卡空闲停车位比率
    private Double this_month;
    //最近半年前面第一个月固定卡空闲停车位比率
    private Double one_month;
    //最近半年前面第二个月固定卡空闲停车位比率
    private Double two_month;
    //最近半年前面第三个月固定卡空闲停车位比率
    private Double three_month;
    //最近半年前面第四个月固定卡空闲停车位比率
    private Double four_month;
    //最近半年前面第五个月固定卡空闲停车位比率
    private Double five_month;
}
