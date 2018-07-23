package cn.xlink.parkinglots.api.service;

import cn.xlink.parkinglots.api.domain.CarInfo;
import cn.xlink.parkinglots.api.page.PageRequest;
import cn.xlink.parkinglots.api.page.PageResponse;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface CarInfoService {
//    public CarInfo findParkingLot(CarInfo car_info);
    public CarInfo save(CarInfo car_info);

    //    public void removeParkingLot(String id);
//
//    public void updateUser(String name, String key, String value);
//
//    public List<ParkingLot> listParkingLot();
    public void delete(CarInfo car_info);
    //有example 的都封装到 sevice里面了，参数不用， 下面不需要了start
//    public ParkingLot findExample(Example<ParkingLot> query);
//    public long findExampleCount(Example<ParkingLot> example);
//    public List<ParkingLot> findAll(Example<ParkingLot> example,Sort sort);
//    public Page<ParkingLot> findAllPage(Example<ParkingLot> example,Pageable pageable);
    //有example 的都封装到 sevice里面了，参数不用， 下面不需要了end

    public CarInfo findEntityOne(CarInfo car_info);

    public List<CarInfo> findAll(CarInfo car_info, Sort sort);
    public PageResponse<CarInfo> findAllPage(CarInfo car_info, PageRequest pageRequest);

}
