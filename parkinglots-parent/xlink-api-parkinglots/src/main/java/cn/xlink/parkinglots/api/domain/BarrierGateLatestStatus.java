package cn.xlink.parkinglots.api.domain;

import cn.xlink.parkinglots.api.page.PageRequest;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**道闸-实时状态*/
@Data
@Getter
@Setter
@Document(collection="barrier_gate_latest_status")
public class BarrierGateLatestStatus implements Serializable{
    //实时信息的实体自动生成_id
    @Id
    private String id;
    //	道闸ID*
    private String gate_id;
    //	停车场ID
    private String parking_id;
    //	是否在线  否0、是1
    private Integer is_online;
    //	运行状态   故障0、正常1
    private Integer gate_running_status;
    //	道闸状态   开启0、关闭1、常开2、常闭3
    private Integer gate_status;
}
