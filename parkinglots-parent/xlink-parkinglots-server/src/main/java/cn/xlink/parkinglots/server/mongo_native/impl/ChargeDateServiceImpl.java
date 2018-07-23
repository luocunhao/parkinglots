package cn.xlink.parkinglots.server.mongo_native.impl;

import cn.xlink.parkinglots.api.domain.ChargeData;
import cn.xlink.parkinglots.api.page.PageCopier;
import cn.xlink.parkinglots.api.page.PageRequest;
import cn.xlink.parkinglots.api.page.PageResponse;
import cn.xlink.parkinglots.api.page.SpringDataPageable;
import cn.xlink.parkinglots.api.service.ChargeDateService;
import cn.xlink.parkinglots.server.mongo_native.dao.ChargeDateRepository;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

@Service(interfaceName = "cn.xlink.parkinglots.api.service.ChargeDateService")
public class ChargeDateServiceImpl implements ChargeDateService{
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    private ChargeDateRepository repository;

    @Override
    public ChargeData save(ChargeData chargeData) {
        return repository.save(chargeData);
//		repository.findAll(example, pageable)
    }

    @Override
    public void delete(ChargeData chargeData) {
        if(chargeData==null){
            repository.deleteAll();
        }
        repository.delete(chargeData);
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
    private Example<ChargeData> createExample(ChargeData chargeData){
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("car_no",ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("billcharge_end",ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("fix_card_value",ExampleMatcher.GenericPropertyMatchers.exact());
        Example<ChargeData> example = Example.of(chargeData,matcher);
        return example;
    }

    @Override
    public ChargeData findEntityOne(ChargeData chargeData) {
        Example<ChargeData> example = createExample(chargeData);
        return repository.findOne(example);
    }

    @Override
    public List<ChargeData> findAll(ChargeData chargeData, Sort sort) {
        Example<ChargeData> example = createExample(chargeData);
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
    public PageResponse<ChargeData> findAllPage(ChargeData chargeData, PageRequest pageRequest) {
        Page<ChargeData> page = null;
        SpringDataPageable pageable = new SpringDataPageable();
        pageable.setPagenumber(pageRequest.getCurrent_page());
        pageable.setPagesize(pageRequest.getPer_page());
        if(chargeData==null){
            page = repository.findAll(pageable);
        }else {
            Example<ChargeData> example = createExample(chargeData);
            page = repository.findAll(example, pageable);
        }
        return PageCopier.copy(page,ChargeData.class);
    }
}
