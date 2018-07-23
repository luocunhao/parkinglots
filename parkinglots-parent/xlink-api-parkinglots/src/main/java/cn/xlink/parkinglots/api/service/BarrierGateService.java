package cn.xlink.parkinglots.api.service;

import cn.xlink.parkinglots.api.domain.BarrierGate;
import cn.xlink.parkinglots.api.page.PageRequest;
import cn.xlink.parkinglots.api.page.PageResponse;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface BarrierGateService {
//    public BarrierGate findParkingLot(BarrierGate barrier_gate);
    public BarrierGate save(BarrierGate barrier_gate);

    //    public void removeParkingLot(String id);
//
//    public void updateUser(String name, String key, String value);
//
//    public List<ParkingLot> listParkingLot();
    public void delete(BarrierGate barrier_gate);
    //有example 的都封装到 sevice里面了，参数不用， 下面不需要了start
//    public ParkingLot findExample(Example<ParkingLot> query);
//    public long findExampleCount(Example<ParkingLot> example);
//    public List<ParkingLot> findAll(Example<ParkingLot> example,Sort sort);
//    public Page<ParkingLot> findAllPage(Example<ParkingLot> example,Pageable pageable);
    //有example 的都封装到 sevice里面了，参数不用， 下面不需要了end

    public BarrierGate findEntityOne(BarrierGate barrier_gate);

    public List<BarrierGate> findAll(BarrierGate barrier_gate, Sort sort);
    public PageResponse<BarrierGate> findAllPage(BarrierGate barrier_gate, PageRequest pageRequest);

}
