package cn.xlink.parkinglots.api.service;

import cn.xlink.parkinglots.api.domain.BarrierGate;
import cn.xlink.parkinglots.api.domain.OutParking;
import cn.xlink.parkinglots.api.domain.ParkingLot;
import cn.xlink.parkinglots.api.page.PageRequest;
import cn.xlink.parkinglots.api.page.PageResponse;
import com.alibaba.fastjson.JSONArray;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface OutParkingService {
//    public OutParking findParkingLot(OutParking out_parking);
    public OutParking save(OutParking out_parking);

    //    public void removeParkingLot(String id);
//
//    public void updateUser(String name, String key, String value);
//
//    public List<ParkingLot> listParkingLot();
    public void delete(OutParking out_parking);
    //有example 的都封装到 sevice里面了，参数不用， 下面不需要了start
//    public ParkingLot findExample(Example<ParkingLot> query);
//    public long findExampleCount(Example<ParkingLot> example);
//    public List<ParkingLot> findAll(Example<ParkingLot> example,Sort sort);
//    public Page<ParkingLot> findAllPage(Example<ParkingLot> example,Pageable pageable);
    //有example 的都封装到 sevice里面了，参数不用， 下面不需要了end
    public long count(OutParking outParking);
    public OutParking findEntityOne(OutParking out_parking);
    public List<OutParking> findAll(OutParking out_parking, Sort sort);
    public PageResponse<OutParking> findAllPage(OutParking out_parking, PageRequest pageRequest);
    public List<OutParking> findAllByTime(OutParking outParking);
    public List<OutParking> findAllByTimeAndParkingId(OutParking outParking);
    //固定卡统计
    public List<OutParking> findListByFixCardValueNeq0(OutParking outParking);
    //收费统计
    public List<OutParking> findListByProjectIdAndFixCardValue(OutParking outParking);
    //道闸流量统计 分组聚合
    public List<BarrierGate> findOutFlowGroupBy(OutParking outParking);
    //支付方式分组
    public ParkingLot findGroupByPayType(OutParking outParking);
    //收费分组聚合统计
    public Double findChargeGroupBy(OutParking outParking);
    //mapreduce
    public JSONArray mapReduce(OutParking outParking);
}
