package cn.xlink.parkinglots.api.domain;

import cn.xlink.parkinglots.api.page.PageRequest;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author luoch
 * @Date 2018-07-12 09:43
 * @Desc
 */
@Data
@Getter
@Setter
public class InParkingPage implements Serializable {
    private InParking inParking;
    private PageRequest pageRequest;
}
