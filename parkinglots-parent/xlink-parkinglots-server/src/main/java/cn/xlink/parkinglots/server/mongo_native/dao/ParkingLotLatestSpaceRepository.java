package cn.xlink.parkinglots.server.mongo_native.dao;

import cn.xlink.parkinglots.api.domain.ParkingLotLatestSpace;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ParkingLotLatestSpaceRepository extends MongoRepository<ParkingLotLatestSpace,String> {
}
