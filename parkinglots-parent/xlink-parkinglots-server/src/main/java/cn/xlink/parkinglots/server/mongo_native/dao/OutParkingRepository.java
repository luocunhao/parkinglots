package cn.xlink.parkinglots.server.mongo_native.dao;

import cn.xlink.parkinglots.api.domain.OutParking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface OutParkingRepository extends MongoRepository<OutParking,String> {
    @Query(value="{project_id:?0,fix_card_value:{$ne:?1},out_time:{$gte:?2,$lte:?3}}")
    List<OutParking> findListByFixCardValueNeq0(String project_id,Integer fix_card_value,String fromTime, String toTime);
    /**
     *
     * project_id 项目Id
     * fix_card_value 固定卡类型
     * fromTime 日期 范围查询
     * toTime
     * @param project_id
     * @param fix_card_value
     * @param fromTime
     * @param toTime
     * @return
     */
    @Query(value="{project_id:?0,fix_card_value:?1,out_time:{$gte:?2,$lte:?3}}")
    List<OutParking> findListByProjectIdAndFixCardValue(String project_id,Integer fix_card_value,String fromTime, String toTime);
    /**
     *
     * project_id 项目id
     * fromTime 日期 范围查询
     * toTime
     * @param project_id
     * @param fromTime
     * @param toTime
     * @return
     */
    @Query(value="{project_id:?0,out_time:{$gte:?1,$lte:?2}}")
    List<OutParking> findListByProjectIdAndOutTime(String project_id,String fromTime,String toTime);

    /**
     *
     * parking_id 车场id
     * fromTime 日期 范围查询
     * toTime
     * @param parking_id
     * @param fromTime
     * @param toTime
     * @return
     */
    @Query(value="{parking_id:?0,out_time:{$gte:?1,$lte:?2}}")
    List<OutParking> findListByParkingIdAndOutTime(String parking_id,String fromTime,String toTime);
}
