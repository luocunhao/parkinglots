package cn.xlink.parkinglots.api.domain;

import cn.xlink.parkinglots.api.page.PageRequest;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author luoch
 * @Date 2018-07-12 13:45
 * @Desc 出场日志分页查询对象
 */
@Setter
@Getter
@Data
public class OutParkingPage implements Serializable {
    private OutParking outParking;
    private PageRequest pageRequest;
}
