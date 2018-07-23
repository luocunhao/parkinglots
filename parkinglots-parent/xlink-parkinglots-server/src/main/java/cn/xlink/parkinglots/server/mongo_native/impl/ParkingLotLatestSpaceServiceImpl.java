package cn.xlink.parkinglots.server.mongo_native.impl;


import cn.xlink.parkinglots.api.domain.ParkingLotLatestSpace;
import cn.xlink.parkinglots.api.page.PageCopier;
import cn.xlink.parkinglots.api.page.PageRequest;
import cn.xlink.parkinglots.api.page.PageResponse;
import cn.xlink.parkinglots.api.page.SpringDataPageable;
import cn.xlink.parkinglots.api.service.ParkingLotLatestSpaceService;
import cn.xlink.parkinglots.server.mongo_native.dao.ParkingLotLatestSpaceRepository;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

@Service(interfaceName = "cn.xlink.parkinglots.api.service.ParkingLotLatestSpaceService")
public class ParkingLotLatestSpaceServiceImpl implements ParkingLotLatestSpaceService {
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    private ParkingLotLatestSpaceRepository repository;

    @Override
    public ParkingLotLatestSpace save(ParkingLotLatestSpace parkingLotLatestSpace) {
        return repository.save(parkingLotLatestSpace);
//		repository.findAll(example, pageable)
    }

    @Override
    public void delete(ParkingLotLatestSpace parkingLotLatestSpace) {
        if(parkingLotLatestSpace==null){
            repository.deleteAll();
        }
        repository.delete(parkingLotLatestSpace);
    }
    /*
    @Override
    public long findExampleCount(Example<ParkingLot> example) {
        if(example==null){
            return repository.count();
        }
        return repository.count(example);
    }
    */

    //组装查询参数
    private Example<ParkingLotLatestSpace> createExample(ParkingLotLatestSpace parkingLotLatestSpace){
        ExampleMatcher matcher = ExampleMatcher.matching();
        Example<ParkingLotLatestSpace> example = Example.of(parkingLotLatestSpace,matcher);
        return example;
    }

    @Override
    public ParkingLotLatestSpace findEntityOne(ParkingLotLatestSpace parkingLotLatestSpace) {
        Example<ParkingLotLatestSpace> example = createExample(parkingLotLatestSpace);
        return repository.findOne(example);
    }

    @Override
    public List<ParkingLotLatestSpace> findAll(ParkingLotLatestSpace parkingLotLatestSpace, Sort sort) {
        Example<ParkingLotLatestSpace> example = createExample(parkingLotLatestSpace);
        if(example!=null&&sort!=null){
            return repository.findAll(example, sort);
        }else if(example!=null&&sort==null) {
            return repository.findAll(example);
        }else if(example==null&&sort!=null) {
            return repository.findAll(sort);
        }
        return repository.findAll();
    }

    @Override
    public PageResponse<ParkingLotLatestSpace> findAllPage(ParkingLotLatestSpace parkingLotLatestSpace, PageRequest pageRequest) {
        Page<ParkingLotLatestSpace> page = null;
        SpringDataPageable pageable = new SpringDataPageable();
        pageable.setPagenumber(pageRequest.getCurrent_page());
        pageable.setPagesize(pageRequest.getPer_page());
        if(parkingLotLatestSpace==null){
            page = repository.findAll(pageable);
        }else {
            Example<ParkingLotLatestSpace> example = createExample(parkingLotLatestSpace);
            page = repository.findAll(example, pageable);
        }
        return PageCopier.copy(page,ParkingLotLatestSpace.class);
    }
}
