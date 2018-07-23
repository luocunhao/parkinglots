package cn.xlink.parkinglots.api.service;


import cn.xlink.parkinglots.api.domain.BarrierGateLatestStatus;
import cn.xlink.parkinglots.api.page.PageRequest;
import cn.xlink.parkinglots.api.page.PageResponse;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface BarrierGateLatestStatusService {
//    public BarrierGateLatestStatus findParkingLot(BarrierGateLatestStatus barrier_gate_latest_status);
    public BarrierGateLatestStatus save(BarrierGateLatestStatus barrier_gate_latest_status);

    //    public void removeParkingLot(String id);
//
//    public void updateUser(String name, String key, String value);
//
//    public List<ParkingLot> listParkingLot();
    public void delete(BarrierGateLatestStatus barrier_gate_latest_status);
    //有example 的都封装到 sevice里面了，参数不用， 下面不需要了start
//    public ParkingLot findExample(Example<ParkingLot> query);
//    public long findExampleCount(Example<ParkingLot> example);
//    public List<ParkingLot> findAll(Example<ParkingLot> example,Sort sort);
//    public Page<ParkingLot> findAllPage(Example<ParkingLot> example,Pageable pageable);
    //有example 的都封装到 sevice里面了，参数不用， 下面不需要了end

    public BarrierGateLatestStatus findEntityOne(BarrierGateLatestStatus barrier_gate_latest_status);

    public List<BarrierGateLatestStatus> findAll(BarrierGateLatestStatus barrier_gate_latest_status, Sort sort);
    public PageResponse<BarrierGateLatestStatus> findAllPage(BarrierGateLatestStatus barrier_gate_latest_status, PageRequest pageRequest);

}
