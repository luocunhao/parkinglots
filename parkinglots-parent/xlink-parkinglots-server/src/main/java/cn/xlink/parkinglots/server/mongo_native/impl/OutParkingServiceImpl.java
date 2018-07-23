package cn.xlink.parkinglots.server.mongo_native.impl;

import cn.xlink.parkinglots.api.domain.BarrierGate;
import cn.xlink.parkinglots.api.domain.InParking;
import cn.xlink.parkinglots.api.domain.OutParking;
import cn.xlink.parkinglots.api.domain.ParkingLot;
import cn.xlink.parkinglots.api.page.PageCopier;
import cn.xlink.parkinglots.api.page.PageRequest;
import cn.xlink.parkinglots.api.page.PageResponse;
import cn.xlink.parkinglots.api.page.SpringDataPageable;
import cn.xlink.parkinglots.api.service.OutParkingService;
import cn.xlink.parkinglots.server.mongo_native.dao.OutParkingRepository;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service(interfaceName = "cn.xlink.parkinglots.api.service.OutParkingService")
public class OutParkingServiceImpl implements OutParkingService {
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    private OutParkingRepository repository;

    @Override
    public OutParking save(OutParking outParking) {
        return repository.save(outParking);
//		repository.findAll(example, pageable)
    }

    @Override
    public void delete(OutParking outParking) {
        if (outParking == null) {
            repository.deleteAll();
        }
        repository.delete(outParking);
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
    private Example<OutParking> createExample(OutParking outParking) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("car_no", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("fix_card_value", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("pay_type", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("parking_time_type", ExampleMatcher.GenericPropertyMatchers.exact());
        Example<OutParking> example = Example.of(outParking, matcher);
        return example;
    }

    @Override
    public OutParking findEntityOne(OutParking outParking) {
        Example<OutParking> example = createExample(outParking);
        return repository.findOne(example);
    }

    @Override
    public List<OutParking> findAll(OutParking outParking, Sort sort) {
        Example<OutParking> example = createExample(outParking);
        if (example != null && sort != null) {
            return repository.findAll(example, sort);
        } else if (example != null && sort == null) {
            return repository.findAll(example);
        } else if (example == null && sort != null) {
            return repository.findAll(sort);
        }
        return repository.findAll();
    }

    @Override
    public PageResponse<OutParking> findAllPage(OutParking outParking, PageRequest pageRequest) {
        Page<OutParking> page = null;
        SpringDataPageable pageable = new SpringDataPageable();
        pageable.setPagenumber(pageRequest.getCurrent_page());
        pageable.setPagesize(pageRequest.getPer_page());
        if (outParking == null) {
            page = repository.findAll(pageable);
        } else {
            Example<OutParking> example = createExample(outParking);
            page = repository.findAll(example, pageable);
        }
        return PageCopier.copy(page, OutParking.class);
    }

    @Override
    public List<OutParking> findAllByTime(OutParking outParking) {
        List<OutParking> list = repository.findListByProjectIdAndOutTime(outParking.getProject_id(), outParking.getStart_time(), outParking.getEnd_time());
        return list;
    }

    @Override
    public List<OutParking> findAllByTimeAndParkingId(OutParking outParking) {
        List<OutParking> list = repository.findListByParkingIdAndOutTime(outParking.getParking_id(), outParking.getStart_time(), outParking.getEnd_time());
        return list;
    }

    @Override
    public List<OutParking> findListByProjectIdAndFixCardValue(OutParking outParking) {
        List<OutParking> list = repository.findListByProjectIdAndFixCardValue(outParking.getProject_id(), outParking.getFix_card_value(), outParking.getStart_time(), outParking.getEnd_time());
        return list;
    }

    @Override
    public List<OutParking> findListByFixCardValueNeq0(OutParking outParking) {
        List<OutParking> list = repository.findListByFixCardValueNeq0(outParking.getProject_id(), outParking.getFix_card_value(), outParking.getStart_time(), outParking.getEnd_time());
        return list;
    }


    @Override
    public long count(OutParking outParking) {
        Criteria criteria = Criteria.where("project_id").is(outParking.getProject_id())
                .and("parking_id").is(outParking.getParking_id())
                .and("gate_id").is(outParking.getGate_id())
                .and("fix_card_value").is(outParking.getFix_card_value())
                .and("out_time").gte(outParking.getStart_time())
                .lte(outParking.getEnd_time());
        Query query = Query.query(criteria);
        long count = mongoTemplate.count(query, "out_parking");
        return count;
    }

    @Override
    public List<BarrierGate> findOutFlowGroupBy(OutParking outParkingExample) {
//        Aggregation aggregation = Aggregation.newAggregation(Aggregation.group("parking_id","gate_id","fix_card_value").count().as("flow"));
//        AggregationResults<BasicDBObject> results =
//                mongoTemplate.aggregate(aggregation,"out_parking",BasicDBObject.class);
//        Iterator<BasicDBObject> iterator = results.iterator();
//        while (iterator.hasNext()){
//            DBObject obj = iterator.next();
//            System.out.println(JSON.toJSONString(obj));
//        }
        Criteria criteria = Criteria.where("project_id").is(outParkingExample.getProject_id())
                .and("out_time").gte(outParkingExample.getStart_time())
                .lte(outParkingExample.getEnd_time());
        GroupBy groupBy = new GroupBy("parking_id", "gate_id")
                .initialDocument("{count:0}")
                .reduceFunction("function (doc,pre){ pre.count +=1;}");
        GroupByResults groupByResults = mongoTemplate.
                group(criteria, "out_parking", groupBy, OutParking.class);
        DBObject dbObject = groupByResults.getRawResults();
        BasicDBList list = (BasicDBList) dbObject.get("retval");
        List<BarrierGate> ret = list.stream().map(map -> {
            BasicDBObject obj = (BasicDBObject) map;
            String parking_id = obj.getString("parking_id");
            String gate_id = obj.getString("gate_id");
            Double count = obj.getDouble("count");
            BarrierGate barrierGate = new BarrierGate();
            barrierGate.setId(gate_id);
            barrierGate.setGate_type(1);
            barrierGate.setParking_id(parking_id);
            barrierGate.setIc_total_count(count.intValue());
            return barrierGate;
        }).collect(Collectors.toList());
        return ret;
    }

    @Override
    public ParkingLot findGroupByPayType(OutParking outParking) {
        ParkingLot parkingLot = new ParkingLot();
        Criteria criteria = Criteria.where("project_id").is(outParking.getProject_id())
                .and("out_time").gte(outParking.getStart_time())
                .lte(outParking.getEnd_time());
        GroupBy groupBy = new GroupBy("pay_type")
                .initialDocument("{count:0}")
                .reduceFunction("function (doc,pre){ pre.count +=1;}");
        GroupByResults groupByResults = mongoTemplate.
                group(criteria, "out_parking", groupBy, OutParking.class);
        DBObject dbObject = groupByResults.getRawResults();
        BasicDBList list = (BasicDBList) dbObject.get("retval");
        list.stream().forEach(map -> {
            BasicDBObject obj = (BasicDBObject) map;
            Double pay_type = obj.getDouble("pay_type");
            Double count = obj.getDouble("count");
            switch (pay_type.intValue()) {
                //现金
                case (0):
                    parkingLot.setMoney(count.intValue());
                    break;
                //微信
                case (1):
                    parkingLot.setWechat(count.intValue());
                    break;
                //支付宝
                case (2):
                    parkingLot.setAlipay(count.intValue());
                    break;
                //其他
                default:
                    parkingLot.setOther(count.intValue());
            }
        });
        return parkingLot;
    }

    @Override
    public Double findChargeGroupBy(OutParking outParking) {
        String project_id = outParking.getProject_id();
        Criteria criteria = Criteria.where("out_time")
                .gte(outParking.getStart_time())
                .lte(outParking.getEnd_time());
        GroupBy groupBy;
        if (!StringUtils.isEmpty(project_id)) {
            criteria.and("project_id").is(outParking.getProject_id());
            groupBy = new GroupBy("project_id");
        } else {
            criteria.and("parking_id").is(outParking.getParking_id());
            groupBy = new GroupBy("parking_id");
        }
        groupBy.initialDocument("{count:0}")
                .reduceFunction("function (doc,pre){ pre.count +=doc.ss_money;}");
        GroupByResults groupByResults = mongoTemplate.
                group(criteria, "out_parking", groupBy, OutParking.class);
        DBObject dbObject = groupByResults.getRawResults();
        BasicDBList list = (BasicDBList) dbObject.get("retval");
        if (list.size() > 0) {
            BasicDBObject obj = (BasicDBObject) list.get(0);
            return obj.getDouble("count");
        } else {
            return 0.0;
        }
    }

    @Override
    public JSONArray mapReduce(OutParking outParking) {
        JSONArray jsonArray = new JSONArray();
        Criteria criteria = Criteria.where("parking_id").is(outParking.getParking_id())
                .and("out_time").gte(outParking.getStart_time())
                .lte(outParking.getEnd_time());
        String map = "function(){emit(this.out_time.substring(0,16),{count:1});}";
        String reduce = "function(key,values){var total = 0;for(var i=0;i<values.length;i++){total += values[i].count;} return total;}";
        Query query = new Query(criteria);
        MapReduceResults< BasicDBObject> mapReduceResults =
                mongoTemplate.mapReduce(query,"out_parking",map,reduce, BasicDBObject.class);
        Iterator iterator = mapReduceResults.iterator();

        while (iterator.hasNext()){
            String jsonString = iterator.next().toString();
            JSONObject jsonObject = JSONObject.parseObject(jsonString);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }
}
