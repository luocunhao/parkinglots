package cn.xlink.parkinglots.server.mongo_native.impl;

import cn.xlink.parkinglots.api.domain.BarrierGate;
import cn.xlink.parkinglots.api.page.PageCopier;
import cn.xlink.parkinglots.api.page.PageRequest;
import cn.xlink.parkinglots.api.page.PageResponse;
import cn.xlink.parkinglots.api.page.SpringDataPageable;
import cn.xlink.parkinglots.api.service.BarrierGateService;
import cn.xlink.parkinglots.server.mongo_native.dao.BarrierGateRepository;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

@Service(interfaceName = "cn.xlink.parkinglots.api.service.BarrierGateService")
public class BarrierGateServiceImpl implements BarrierGateService{
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    private BarrierGateRepository repository;

    @Override
    public BarrierGate save(BarrierGate barrierGate) {
        return repository.save(barrierGate);
//		repository.findAll(example, pageable)
    }

    @Override
    public void delete(BarrierGate barrierGate) {
        if(barrierGate==null){
            repository.deleteAll();
        }
        repository.delete(barrierGate);
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
    private Example<BarrierGate> createExample(BarrierGate barrierGate){
        ExampleMatcher matcher = ExampleMatcher.matching();
        Example<BarrierGate> example = Example.of(barrierGate,matcher);
        return example;
    }

    @Override
    public BarrierGate findEntityOne(BarrierGate barrierGate) {
        Example<BarrierGate> example = createExample(barrierGate);
        return repository.findOne(example);
    }

    @Override
    public List<BarrierGate> findAll(BarrierGate barrierGate, Sort sort) {
        Example<BarrierGate> example = createExample(barrierGate);
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
    public PageResponse<BarrierGate> findAllPage(BarrierGate barrierGate, PageRequest pageRequest) {
        Page<BarrierGate> page = null;
        SpringDataPageable pageable = new SpringDataPageable();
        pageable.setPagenumber(pageRequest.getCurrent_page());
        pageable.setPagesize(pageRequest.getPer_page());
        if(barrierGate==null){
            page = repository.findAll(pageable);
        }else {
            Example<BarrierGate> example = createExample(barrierGate);
            page = repository.findAll(example, pageable);
        }
        return PageCopier.copy(page,BarrierGate.class);
    }
}
