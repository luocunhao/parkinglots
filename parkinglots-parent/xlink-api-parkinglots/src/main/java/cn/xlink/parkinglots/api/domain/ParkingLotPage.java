package cn.xlink.parkinglots.api.domain;

import java.io.Serializable;

import cn.xlink.parkinglots.api.page.PageRequest;
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
public class ParkingLotPage implements Serializable{
	private ParkingLot parkingLot;
	private PageRequest pageRequest;
}
