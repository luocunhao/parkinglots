package cn.xlink.parkinglots.api.service;

import cn.xlink.parkinglots.api.domain.BarrierGate;
import cn.xlink.parkinglots.api.domain.InParking;
import cn.xlink.parkinglots.api.page.PageRequest;
import cn.xlink.parkinglots.api.page.PageResponse;
import com.alibaba.fastjson.JSONArray;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface InParkingService {
//    public InParking findParkingLot(InParking in_parking);
    public InParking save(InParking in_parking);

    //    public void removeParkingLot(String id);
//
//    public void updateUser(String name, String key, String value);
//
//    public List<ParkingLot> listParkingLot();
    public void delete(InParking in_parking);
    //有example 的都封装到 sevice里面了，参数不用， 下面不需要了start
//    public ParkingLot findExample(Example<ParkingLot> query);
//    public long findExampleCount(Example<ParkingLot> example);
//    public List<ParkingLot> findAll(Example<ParkingLot> example,Sort sort);
//    public Page<ParkingLot> findAllPage(Example<ParkingLot> example,Pageable pageable);
    //有example 的都封装到 sevice里面了，参数不用， 下面不需要了end
    public long count(InParking inParking);
    public InParking findEntityOne(InParking in_parking);

    public List<InParking> findAll(InParking in_parking, Sort sort);
    public PageResponse<InParking> findAllPage(InParking in_parking, PageRequest pageRequest);
    //道闸流量统计 parking_id和gate_id分组聚合
    public List<BarrierGate> findOutFlowGroupBy(InParking inParking);
    public JSONArray inParkingMapReduce(InParking inParking);
}
