package cn.xlink.parkinglots.server.mongo_native.dao;

import cn.xlink.parkinglots.api.domain.ParkingLot;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ParkingLotManageRepository extends MongoRepository<ParkingLot, String> {
}
