package cn.xlink.parkinglots.server.mongo_native.dao;

import cn.xlink.parkinglots.api.domain.ParkingLot;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ParkingLotsRepository extends MongoRepository<ParkingLot, String> {

	/**
	 * 
	 * name 模糊name查询 
	 * gate_count 大小 范围查询
	 * update_time 日期 范围查询
	 * 
	 * @param name
	 * @param from
	 * @param to
	 * @param fromTime
	 * @param toTime
	 * @param pageable
	 * @return
	 */
	@Query(value="{ 'name':{$regex:?0,$options:'i'},'gate_count':{$gte:?1,$lte:?2},'update_time':{$gte:?3,$lte:?4}}") 
	public Page<ParkingLot> findAllPageLike(String name,Integer from,Integer to,String fromTime,String toTime,Pageable pageable);
}
