package cn.xlink.parkinglots.server.mongo_native.impl;

import cn.xlink.parkinglots.api.domain.BarrierGate;
import cn.xlink.parkinglots.api.domain.InParking;
import cn.xlink.parkinglots.api.page.PageCopier;
import cn.xlink.parkinglots.api.page.PageRequest;
import cn.xlink.parkinglots.api.page.PageResponse;
import cn.xlink.parkinglots.api.page.SpringDataPageable;
import cn.xlink.parkinglots.api.service.InParkingService;
import cn.xlink.parkinglots.server.mongo_native.dao.InParkingRepository;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service(interfaceName = "cn.xlink.parkinglots.api.service.InParkingService")
public class InParkingServiceImpl implements InParkingService {
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    private InParkingRepository repository;

    @Override
    public InParking save(InParking inParking) {
        return repository.save(inParking);
//		repository.findAll(example, pageable)
    }

    @Override
    public void delete(InParking inParking) {
        if (inParking == null) {
            repository.deleteAll();
        }
        repository.delete(inParking);
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
    private Example<InParking> createExample(InParking inParking) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("fix_card_value", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("parking_time_type", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("car_no", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<InParking> example = Example.of(inParking, matcher);
        return example;
    }

    @Override
    public InParking findEntityOne(InParking inParking) {
        Example<InParking> example = createExample(inParking);
        return repository.findOne(example);
    }

    @Override
    public long count(InParking inParking) {
        Criteria criteria = Criteria.where("project_id").is(inParking.getProject_id())
                .and("parking_id").is(inParking.getParking_id())
                .and("gate_id").is(inParking.getGate_id())
                .and("fix_card_value").is(inParking.getFix_card_value())
                .and("in_time").gte(inParking.getStart_time())
                .lte(inParking.getEnd_time());
        Query query = Query.query(criteria);
        long count = mongoTemplate.count(query,"in_parking");
        return count;
    }

    @Override
    public List<InParking> findAll(InParking inParking, Sort sort) {
        Example<InParking> example = createExample(inParking);
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
    public PageResponse<InParking> findAllPage(InParking inParking, PageRequest pageRequest) {
        Page<InParking> page = null;
        SpringDataPageable pageable = new SpringDataPageable();
        pageable.setPagenumber(pageRequest.getCurrent_page());
        pageable.setPagesize(pageRequest.getPer_page());
        if (inParking == null) {
            page = repository.findAll(pageable);
        } else {
            Example<InParking> example = createExample(inParking);
            page = repository.findAll(example, pageable);
        }
        return PageCopier.copy(page, InParking.class);
    }

    @Override
    public List<BarrierGate> findOutFlowGroupBy(InParking inParkingExample) {
        Criteria criteria = Criteria.where("project_id").is(inParkingExample.getProject_id())
                .and("in_time").gte(inParkingExample.getStart_time())
                .lte(inParkingExample.getEnd_time());
        GroupBy groupBy = new GroupBy("parking_id", "gate_id")
                .initialDocument("{count:0}")
                .reduceFunction("function (doc,pre){ pre.count +=1;}");
        GroupByResults groupByResults = mongoTemplate.
                group(criteria, "in_parking", groupBy, InParking.class);
        DBObject dbObject = groupByResults.getRawResults();
        BasicDBList list = (BasicDBList) dbObject.get("retval");
        List<BarrierGate> ret = list.stream().map(map -> {
            BasicDBObject obj = (BasicDBObject) map;
            String parking_id = obj.getString("parking_id");
            String gate_id = obj.getString("gate_id");
            Double count = obj.getDouble("count");
            BarrierGate barrierGate = new BarrierGate();
            barrierGate.setIc_total_count(count.intValue());
            barrierGate.setId(gate_id);
            barrierGate.setGate_type(0);
            barrierGate.setParking_id(parking_id);
            return barrierGate;
        }).collect(Collectors.toList());
        return ret;
    }
    @Override
    public JSONArray inParkingMapReduce(InParking inParking) {
        JSONArray jsonArray = new JSONArray();
        Criteria criteria = Criteria.where("parking_id").is(inParking.getParking_id())
                .and("in_time").gte(inParking.getStart_time())
                .lte(inParking.getEnd_time());
        String map = "function(){emit(this.in_time.substring(0,16),{count:1});}";
        String reducef = "function(key,values){var total = 0;for(var i=0;i<values.length;i++){total += values[i].count;} return total;}";
        Query query = new Query(criteria);
        MapReduceResults<BasicDBObject> mapReduceResults =
                mongoTemplate.mapReduce(query,"in_parking",map,reducef,BasicDBObject.class);
        Iterator iterator = mapReduceResults.iterator();
        while (iterator.hasNext()){
            String jsonString = iterator.next().toString();
            JSONObject jsonObject = JSONObject.parseObject(jsonString);
            JSONObject valueObj = jsonObject.getJSONObject("value");
            Double count = valueObj.getDouble("count");
            String key = jsonObject.getString("_id");
            JSONObject ret = new JSONObject();
            ret.put("_id",key);
            ret.put("count",count);
            jsonArray.add(ret);
        }
        return jsonArray;
    }
}
