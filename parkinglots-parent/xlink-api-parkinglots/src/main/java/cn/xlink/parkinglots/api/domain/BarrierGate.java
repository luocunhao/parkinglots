package cn.xlink.parkinglots.api.domain;

import cn.xlink.parkinglots.api.page.PageRequest;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**道闸基本信息*/
@Data
@Getter
@Setter
@Document(collection="barrier_gate")
public class BarrierGate implements Serializable{
    //道闸id
    @Id
    private String id;
    private String project_id;
//    //道闸id
////    private String gate_id;
    //停车场id
    private String parking_id;
    //设备创建时间
    private String create_time;
    //道闸的编号
    private String gate_code;
    //道闸的名称
    private String gate_name;
    //道闸品牌
    private String gate_brand;
    //道闸型号
    private String gate_model;
    //进出闸类型   进闸0、出闸1
    private Integer gate_type;
    //道闸实时数据
    private BarrierGateLatestStatus barrierGateLatestStatus;

    //业务数据
    private ParkingLot parkingLot;
    //临停车流量
    private Integer ic_temporary_count;
    //总流量
    private Integer ic_total_count;
    //开始时间
    private String start_time;
    //结束时间
    private String end_time;
}
