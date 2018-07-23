package cn.xlink.parkinglots.api.service;


import cn.xlink.parkinglots.api.domain.ChargeData;
import cn.xlink.parkinglots.api.page.PageRequest;
import cn.xlink.parkinglots.api.page.PageResponse;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ChargeDateService {
//    public ChargeData findParkingLot(ChargeData charge_date);
    public ChargeData save(ChargeData charge_date);

    //    public void removeParkingLot(String id);
//
//    public void updateUser(String name, String key, String value);
//
//    public List<ParkingLot> listParkingLot();
    public void delete(ChargeData charge_date);
    //有example 的都封装到 sevice里面了，参数不用， 下面不需要了start
//    public ParkingLot findExample(Example<ParkingLot> query);
//    public long findExampleCount(Example<ParkingLot> example);
//    public List<ParkingLot> findAll(Example<ParkingLot> example,Sort sort);
//    public Page<ParkingLot> findAllPage(Example<ParkingLot> example,Pageable pageable);
    //有example 的都封装到 sevice里面了，参数不用， 下面不需要了end

    public ChargeData findEntityOne(ChargeData charge_date);

    public List<ChargeData> findAll(ChargeData charge_date, Sort sort);
    public PageResponse<ChargeData> findAllPage(ChargeData charge_date, PageRequest pageRequest);

}
