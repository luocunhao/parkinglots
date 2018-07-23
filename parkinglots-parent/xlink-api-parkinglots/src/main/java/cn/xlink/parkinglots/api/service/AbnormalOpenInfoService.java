package cn.xlink.parkinglots.api.service;

import cn.xlink.parkinglots.api.domain.AbnormalOpenInfo;
import cn.xlink.parkinglots.api.domain.ParkingLot;
import cn.xlink.parkinglots.api.page.PageRequest;
import cn.xlink.parkinglots.api.page.PageResponse;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface AbnormalOpenInfoService {
//    public AbnormalOpenInfo findParkingLot(AbnormalOpenInfo abnormal_open_info);
    public AbnormalOpenInfo save(AbnormalOpenInfo abnormal_open_info);

    //    public void removeParkingLot(String id);
//
//    public void updateUser(String name, String key, String value);
//
//    public List<ParkingLot> listParkingLot();
    public void delete(AbnormalOpenInfo abnormal_open_info);
    //有example 的都封装到 sevice里面了，参数不用， 下面不需要了start
//    public ParkingLot findExample(Example<ParkingLot> query);
//    public long findExampleCount(Example<ParkingLot> example);
//    public List<ParkingLot> findAll(Example<ParkingLot> example,Sort sort);
//    public Page<ParkingLot> findAllPage(Example<ParkingLot> example,Pageable pageable);
    //有example 的都封装到 sevice里面了，参数不用， 下面不需要了end

    public AbnormalOpenInfo findEntityOne(AbnormalOpenInfo abnormal_open_info);

    public List<AbnormalOpenInfo> findAll(AbnormalOpenInfo abnormal_open_info, Sort sort);
    public PageResponse<AbnormalOpenInfo> findAllPage(AbnormalOpenInfo abnormal_open_info, PageRequest pageRequest);
}
