package cn.xlink.parkinglots.server.mongo_native.impl;

import cn.xlink.parkinglots.api.domain.AbnormalOpenInfo;
import cn.xlink.parkinglots.api.page.PageCopier;
import cn.xlink.parkinglots.api.page.PageRequest;
import cn.xlink.parkinglots.api.page.PageResponse;
import cn.xlink.parkinglots.api.page.SpringDataPageable;
import cn.xlink.parkinglots.api.service.AbnormalOpenInfoService;
import cn.xlink.parkinglots.server.mongo_native.dao.AbnormalOpenInfoRepository;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

@Service(interfaceName = "cn.xlink.parkinglots.api.service.AbnormalOpenInfoService")
public class AbnormalOpenInfoServiceImpl implements AbnormalOpenInfoService {
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    private AbnormalOpenInfoRepository repository;

    @Override
    public AbnormalOpenInfo save(AbnormalOpenInfo abnormalOpenInfo) {
        return repository.save(abnormalOpenInfo);
//		repository.findAll(example, pageable)
    }

    @Override
    public void delete(AbnormalOpenInfo abnormalOpenInfo) {
        if(abnormalOpenInfo==null){
            repository.deleteAll();
        }
        repository.delete(abnormalOpenInfo);
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
    private Example<AbnormalOpenInfo> createExample(AbnormalOpenInfo abnormalOpenInfo){
        ExampleMatcher matcher = ExampleMatcher.matching();
        Example<AbnormalOpenInfo> example = Example.of(abnormalOpenInfo,matcher);
        return example;
    }

    @Override
    public AbnormalOpenInfo findEntityOne(AbnormalOpenInfo abnormalOpenInfo) {
        Example<AbnormalOpenInfo> example = createExample(abnormalOpenInfo);
        return repository.findOne(example);
    }

    @Override
    public List<AbnormalOpenInfo> findAll(AbnormalOpenInfo abnormalOpenInfo, Sort sort) {
        Example<AbnormalOpenInfo> example = createExample(abnormalOpenInfo);
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
    public PageResponse<AbnormalOpenInfo> findAllPage(AbnormalOpenInfo abnormalOpenInfo, PageRequest pageRequest) {
        Page<AbnormalOpenInfo> page = null;
        SpringDataPageable pageable = new SpringDataPageable();
        pageable.setPagenumber(pageRequest.getCurrent_page());
        pageable.setPagesize(pageRequest.getPer_page());
        if(abnormalOpenInfo==null){
            page = repository.findAll(pageable);
        }else {
            Example<AbnormalOpenInfo> example = createExample(abnormalOpenInfo);
            page = repository.findAll(example, pageable);
        }
        return PageCopier.copy(page,AbnormalOpenInfo.class);
    }
}
