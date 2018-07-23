package cn.xlink.parkinglots.server.mongo_native.impl;

import cn.xlink.parkinglots.api.domain.CarInfo;
import cn.xlink.parkinglots.api.page.PageCopier;
import cn.xlink.parkinglots.api.page.PageRequest;
import cn.xlink.parkinglots.api.page.PageResponse;
import cn.xlink.parkinglots.api.page.SpringDataPageable;
import cn.xlink.parkinglots.api.service.CarInfoService;
import cn.xlink.parkinglots.server.mongo_native.dao.CarInfoRepository;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

@Service(interfaceName = "cn.xlink.parkinglots.api.service.CarInfoService")
public class CarInfoServiceImpl implements CarInfoService {
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    private CarInfoRepository repository;

    @Override
    public CarInfo save(CarInfo carInfo) {
        return repository.save(carInfo);
//		repository.findAll(example, pageable)
    }

    @Override
    public void delete(CarInfo carInfo) {
        if(carInfo==null){
            repository.deleteAll();
        }
        repository.delete(carInfo);
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
    private Example<CarInfo> createExample(CarInfo carInfo){
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("car_no",ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("user_name",ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("user_mobile",ExampleMatcher.GenericPropertyMatchers.contains());

        Example<CarInfo> example = Example.of(carInfo,matcher);
        return example;
    }

    @Override
    public CarInfo findEntityOne(CarInfo carInfo) {
        Example<CarInfo> example = createExample(carInfo);
        return repository.findOne(example);
    }

    @Override
    public List<CarInfo> findAll(CarInfo carInfo, Sort sort) {
        Example<CarInfo> example = createExample(carInfo);
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
    public PageResponse<CarInfo> findAllPage(CarInfo carInfo, PageRequest pageRequest) {
        Page<CarInfo> page = null;
        SpringDataPageable pageable = new SpringDataPageable();
        pageable.setPagenumber(pageRequest.getCurrent_page());
        pageable.setPagesize(pageRequest.getPer_page());
        if(carInfo==null){
            page = repository.findAll(pageable);
        }else {
            Example<CarInfo> example = createExample(carInfo);
            page = repository.findAll(example, pageable);
        }
        return PageCopier.copy(page,CarInfo.class);
    }
}
