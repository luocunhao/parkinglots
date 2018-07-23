package cn.xlink.parkinglots.server.mongo_native.dao;

import cn.xlink.parkinglots.api.domain.AbnormalOpenInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AbnormalOpenInfoRepository extends MongoRepository<AbnormalOpenInfo, String>{
}
