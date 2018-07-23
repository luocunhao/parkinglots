package cn.xlink.parkinglots.api.domain;

import cn.xlink.parkinglots.api.page.PageRequest;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author luoch
 * @Date 2018-07-17 16:40
 * @Desc
 */
@Data
@Getter
@Setter
public class ChargeDataPage implements Serializable{
    private ChargeData chargeData;
    private PageRequest pageRequest;
}
