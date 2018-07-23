package cn.xlink.parkinglots.server.mongo_native.impl;

import cn.xlink.parkinglots.api.domain.ParkingLot;
import cn.xlink.parkinglots.api.page.PageRequest;
import cn.xlink.parkinglots.api.page.PageResponse;
import cn.xlink.parkinglots.api.service.ParkingLotManageService;
import cn.xlink.parkinglots.server.mongo_native.dao.ParkingLotManageRepository;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

@Service(interfaceName = "cn.xlink.parkinglots.api.service.ParkingLotManageService")
public class ParkingLotManageServiceImpl implements ParkingLotManageService {

    @Autowired
    ParkingLotManageRepository parkingLotManageRepository;
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public ParkingLot findParkingLot(ParkingLot parkingLot) {
        return parkingLotManageRepository.findOne(parkingLot.getId());
    }

    @Override
    public ParkingLot save(ParkingLot parkingLot) {

        return parkingLotManageRepository.save(parkingLot);
    }

    @Override
    public void delete(ParkingLot parkingLot) {
        if(parkingLot==null){
            parkingLotManageRepository.deleteAll();
        }
        parkingLotManageRepository.delete(parkingLot);
    }

    @Override
    public ParkingLot findEntityOne(ParkingLot parkingLot) {

        Example<ParkingLot> example = createExample(parkingLot);
        return parkingLotManageRepository.findOne(example);
    }

    @Override
    public List<ParkingLot> findAll(ParkingLot parkingLot, Sort sort) {
        return null;
    }

    @Override
    public PageResponse<ParkingLot> findAllPage(ParkingLot parkingLot, PageRequest pageRequest) {

        return null;
    }
    //组装查询参数
    private Example<ParkingLot> createExample(ParkingLot parkingLot){
        ExampleMatcher matcher = ExampleMatcher.matching();
        Example<ParkingLot> example = Example.of(parkingLot,matcher);
        return example;
    }
}
