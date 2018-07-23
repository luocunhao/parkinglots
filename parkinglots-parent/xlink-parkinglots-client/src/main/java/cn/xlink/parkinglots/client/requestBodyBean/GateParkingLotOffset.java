package cn.xlink.parkinglots.client.requestBodyBean;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class GateParkingLotOffset {
    private int offset;
    private int count;
}
