package cn.xlink.parkinglots.server.mongo_native.impl;

import cn.xlink.parkinglots.api.domain.BarrierGateLatestStatus;
import cn.xlink.parkinglots.api.page.PageCopier;
import cn.xlink.parkinglots.api.page.PageRequest;
import cn.xlink.parkinglots.api.page.PageResponse;
import cn.xlink.parkinglots.api.page.SpringDataPageable;
import cn.xlink.parkinglots.api.service.BarrierGateLatestStatusService;
import cn.xlink.parkinglots.server.mongo_native.dao.BarrierGateLatestStatusRepository;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

@Service(interfaceName = "cn.xlink.parkinglots.api.service.BarrierGateLatestStatusService")
public class BarrierGateLatestStatusServiceImpl implements BarrierGateLatestStatusService {
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    private BarrierGateLatestStatusRepository repository;

    @Override
    public BarrierGateLatestStatus save(BarrierGateLatestStatus barrierGateLatestStatus) {
        return repository.save(barrierGateLatestStatus);
//		repository.findAll(example, pageable)
    }

    @Override
    public void delete(BarrierGateLatestStatus barrierGateLatestStatus) {
        if(barrierGateLatestStatus==null){
            repository.deleteAll();
        }
        repository.delete(barrierGateLatestStatus);
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
    private Example<BarrierGateLatestStatus> createExample(BarrierGateLatestStatus abnormalOpenInfo){
        ExampleMatcher matcher = ExampleMatcher.matching();
        Example<BarrierGateLatestStatus> example = Example.of(abnormalOpenInfo,matcher);
        return example;
    }

    @Override
    public BarrierGateLatestStatus findEntityOne(BarrierGateLatestStatus barrierGateLatestStatus) {
        Example<BarrierGateLatestStatus> example = createExample(barrierGateLatestStatus);
        return repository.findOne(example);
    }

    @Override
    public List<BarrierGateLatestStatus> findAll(BarrierGateLatestStatus barrierGateLatestStatus, Sort sort) {
        Example<BarrierGateLatestStatus> example = createExample(barrierGateLatestStatus);
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
    public PageResponse<BarrierGateLatestStatus> findAllPage(BarrierGateLatestStatus barrierGateLatestStatus, PageRequest pageRequest) {
        Page<BarrierGateLatestStatus> page = null;
        SpringDataPageable pageable = new SpringDataPageable();
        pageable.setPagenumber(pageRequest.getCurrent_page());
        pageable.setPagesize(pageRequest.getPer_page());
        if(barrierGateLatestStatus==null){
            page = repository.findAll(pageable);
        }else {
            Example<BarrierGateLatestStatus> example = createExample(barrierGateLatestStatus);
            page = repository.findAll(example, pageable);
        }
        return PageCopier.copy(page,BarrierGateLatestStatus.class);
    }
}
