package cn.xlink.parkinglots.api.domain;

import cn.xlink.parkinglots.api.page.PageRequest;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
@Getter
@Setter
public class BarrierGatePage implements Serializable{
    private BarrierGate barrierGate;
    private PageRequest pageRequest;
}
