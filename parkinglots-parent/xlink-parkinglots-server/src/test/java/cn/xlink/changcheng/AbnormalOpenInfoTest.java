package cn.xlink.changcheng;


import cn.xlink.parkinglots.api.domain.AbnormalOpenInfo;
import cn.xlink.parkinglots.api.page.PageRequest;
import cn.xlink.parkinglots.api.page.PageResponse;
import cn.xlink.parkinglots.api.service.AbnormalOpenInfoService;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AbnormalOpenInfoService.class)
public class AbnormalOpenInfoTest {
    @Autowired
    private AbnormalOpenInfoService abnormalOpenInfoService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void saveProductInstance() throws Exception{
        AbnormalOpenInfo abnormalOpenInfo = new AbnormalOpenInfo();
//        abnormalOpenInfo.setId(new ObjectId().toString());

        AbnormalOpenInfo p = abnormalOpenInfoService.save(abnormalOpenInfo);
        System.out.println(p);
    }
    //@Test
    public void findProductInstance() throws Exception{
        AbnormalOpenInfo abnormalOpenInfo = new AbnormalOpenInfo();
//        abnormalOpenInfo.setId("1");
        AbnormalOpenInfo p =abnormalOpenInfoService.findEntityOne(abnormalOpenInfo);
        System.out.println("返回数据："+p);

    }

    //@Test
    public void findPage() throws Exception{
        AbnormalOpenInfo abnormalOpenInfo = new AbnormalOpenInfo();
//	parkingLot.setProduct_id("1607d2b63f5f00011607d2b63f5f9401");

        List<AbnormalOpenInfo> list = abnormalOpenInfoService.findAll(abnormalOpenInfo, null);

//	Pageable pageable = new PageRequest(1,10,null);
//	SpringDataPageable pageable = new SpringDataPageable();
//	List<ProductInstance> pInstance = new ArrayList<ProductInstance>();
//	pInstance.add(new ProductInstance(Direction.DESC, new ProductInstance(Direction.DESC, "createTime")));
//	pageable.setSort(new Sort(orders));
        //每页显示条数
//	pageable.setPagesize(10);
////当前页
//	pageable.setPagenumber(2);
        PageRequest pageRequest = new PageRequest();
        pageRequest.setCurrent_page(1);
        pageRequest.setPer_page(10);

        PageResponse<AbnormalOpenInfo> page =abnormalOpenInfoService.findAllPage(abnormalOpenInfo, pageRequest);
        System.out.println("返回example数据："+list);

        System.out.println(page.getTotal());
        System.out.println(page.getClass());
        System.out.println("返回example数据："+page.getList());

    }
}
