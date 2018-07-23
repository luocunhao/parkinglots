package cn.xlink.parkinglots.server.mongo_native.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.alibaba.dubbo.config.annotation.Service;

import cn.xlink.parkinglots.api.domain.ParkingLot;
import cn.xlink.parkinglots.api.page.PageCopier;
import cn.xlink.parkinglots.api.page.PageRequest;
import cn.xlink.parkinglots.api.page.PageResponse;
import cn.xlink.parkinglots.api.page.SpringDataPageable;
import cn.xlink.parkinglots.api.service.ParkingLotService;
import cn.xlink.parkinglots.server.mongo_native.dao.ParkingLotsRepository;

@Service(interfaceName = "cn.xlink.parkinglots.api.service.ParkingLotService")
public class ParkingLotServiceImpl implements ParkingLotService {
	@Autowired
	MongoTemplate mongoTemplate;
	@Autowired
	private ParkingLotsRepository repository;
	@Override
	public ParkingLot findParkingLot(ParkingLot parkingLot) {
//		System.out.println(parkingLot.getId());
//		System.out.println(mongoTemplate.findById(parkingLot.getId(), ParkingLot.class));
////		mongoTemplate.findOne(arg0, arg1);
//		mongoTemplate.f
//		mongoTemplate.findOne(
//                new Query(Criteria.where("_id").), ParkingLot.class);
//		 return mongoTemplate.findOne(dbob
//	                new Query(Criteria.where("_id").is(parkingLot.getId())), ParkingLot.class);
//		return repository.findAll().get(0);
		
//		DBObject queryobject=new BasicDBObject();
//		queryobject.
//		Query query=new BasicQuery(queryObject);
		return repository.findOne(parkingLot.getId());
	}
	@Override
	public ParkingLot save(ParkingLot parkingLot) {
		return repository.save(parkingLot);
//		repository.findAll(example, pageable)
	}
	@Override
	public void delete(ParkingLot parkingLot) {
		if(parkingLot==null){
			repository.deleteAll();
		}
		repository.delete(parkingLot);
	}

//	@Override
//	public ParkingLot findExample(Example<ParkingLot> example) {
////		ParkingLot parkingLot = new ParkingLot();
////		parkingLot.setId("1");
////		Example<ParkingLot> query = Example.of(parkingLot);
//		return repository.findOne(example);
//	}
	
	@Override
	public long findExampleCount(Example<ParkingLot> example) {
		if(example==null){
			return repository.count();
		}
		return repository.count(example);
	}
//	@Override
//	public List<ParkingLot> findAll(Example<ParkingLot> example,Sort sort) {
//		if(example!=null&&sort!=null){
//			return repository.findAll(example, sort);
//		}else if(example!=null&&sort==null) {
//			return repository.findAll(example);
//		}else if(example==null&&sort!=null) {
//			return repository.findAll(sort);
//		}
//
//		return repository.findAll();
//	}
	
	
//	@Override
//	public Page<ParkingLot> findAllPage(Example<ParkingLot> example,Pageable pageable) {
//		if(example==null){
//			return repository.findAll(pageable);
//		}
//		return repository.findAll(example, pageable);
//	}

	@Override
	public ParkingLot findEntityOne(ParkingLot parkingLot) {
		Example<ParkingLot> example = createExample(parkingLot);
			return repository.findOne(example);
	}
	
	//组装查询参数
	private Example<ParkingLot> createExample(ParkingLot parkingLot){
		ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("parking_name",ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("gate_name",ExampleMatcher.GenericPropertyMatchers.contains());
		Example<ParkingLot> example = Example.of(parkingLot,matcher);
		return example;
	}
	@Override
	public List<ParkingLot> findAll(ParkingLot parkingLot, Sort sort) {
		Example<ParkingLot> example = createExample(parkingLot);
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
	public PageResponse<ParkingLot> findAllPage(ParkingLot parkingLot, PageRequest pageRequest) {
		Page<ParkingLot> page = null;
		SpringDataPageable pageable = new SpringDataPageable();
    	pageable.setPagenumber(pageRequest.getCurrent_page());
    	pageable.setPagesize(pageRequest.getPer_page());
		if(parkingLot==null){
			page = repository.findAll(pageable);
		}else {
			Example<ParkingLot> example = createExample(parkingLot);
			page = repository.findAll(example, pageable);
		}
		return PageCopier.copy(page,ParkingLot.class);
	}
}
