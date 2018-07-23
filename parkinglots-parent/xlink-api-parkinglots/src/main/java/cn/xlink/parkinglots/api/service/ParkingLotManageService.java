package cn.xlink.parkinglots.api.service;

import cn.xlink.parkinglots.api.domain.ParkingLot;
import cn.xlink.parkinglots.api.page.PageRequest;
import cn.xlink.parkinglots.api.page.PageResponse;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ParkingLotManageService {
    //	public void saveParkingLot(ParkingLot parkingLot);

    public ParkingLot findParkingLot(ParkingLot parkingLot);
    public ParkingLot save(ParkingLot parkingLot);

    //    public void removeParkingLot(String id);
//
//    public void updateUser(String name, String key, String value);
//
//    public List<ParkingLot> listParkingLot();
    public void delete(ParkingLot parkingLot);
    //有example 的都封装到 sevice里面了，参数不用， 下面不需要了start
//    public ParkingLot findExample(Example<ParkingLot> query);
//    public long findExampleCount(Example<ParkingLot> example);
//    public List<ParkingLot> findAll(Example<ParkingLot> example,Sort sort);
//    public Page<ParkingLot> findAllPage(Example<ParkingLot> example,Pageable pageable);
    //有example 的都封装到 sevice里面了，参数不用， 下面不需要了end

    public ParkingLot findEntityOne(ParkingLot parkingLot);

    public List<ParkingLot> findAll(ParkingLot parkingLot, Sort sort);
    public PageResponse<ParkingLot> findAllPage(ParkingLot parkingLot, PageRequest pageRequest);
}
