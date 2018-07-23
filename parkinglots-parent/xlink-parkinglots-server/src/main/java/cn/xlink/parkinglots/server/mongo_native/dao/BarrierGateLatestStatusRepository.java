package cn.xlink.parkinglots.server.mongo_native.dao;

import cn.xlink.parkinglots.api.domain.BarrierGateLatestStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BarrierGateLatestStatusRepository extends MongoRepository<BarrierGateLatestStatus,String> {
}
