package cn.xlink.parkinglots.server.mongo_native.dao;

import cn.xlink.parkinglots.api.domain.InParking;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InParkingRepository extends MongoRepository<InParking,String> {
}
