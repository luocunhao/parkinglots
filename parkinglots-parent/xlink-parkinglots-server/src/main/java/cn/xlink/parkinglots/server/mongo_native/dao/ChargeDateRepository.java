package cn.xlink.parkinglots.server.mongo_native.dao;

import cn.xlink.parkinglots.api.domain.ChargeData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChargeDateRepository extends MongoRepository<ChargeData,String> {
}
