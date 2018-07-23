package cn.xlink.changcheng;//package cn.xlink.changcheng;


import java.util.List;

import cn.xlink.parkinglots.api.domain.*;
import cn.xlink.parkinglots.api.service.*;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.xlink.ParkinglotsServer;
import cn.xlink.parkinglots.api.page.PageRequest;
import cn.xlink.parkinglots.api.page.PageResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ParkinglotsServer.class)
public class ParkingLotsTest {

    @Autowired
    private ParkingLotService parkingLotService;
    @Autowired
    private BarrierGateService barrierGateService;
    @Autowired
    private CarInfoService carInfoService;
    @Autowired
    private ChargeDateService chargeDateService;
    @Autowired
    private InParkingService inParkingService;
    @Autowired
    private OutParkingService outParkingService;



	@Autowired
	private MongoTemplate mongoTemplate;
	
  @Test
  public void saveProductInstance() throws Exception{
	  ParkingLot parkingLot = new ParkingLot();
	  parkingLot.setAll_book_space(123);
	  parkingLot.setProject_code("6789");
	  parkingLot.setId("666");
	  parkingLot.setParking_name("停车场");
	  parkingLot.setParking_code("01-1");
	  parkingLot.setIn_park_mount(6);
	  parkingLot.setOut_park_mount(6);

	  ParkingLot p = parkingLotService.save(parkingLot);
  	  System.out.println(p);
  }

  @Test
  public void savabarriergate() throws Exception{
      BarrierGate barrierGate=new BarrierGate();
      barrierGate.setId("01");
      barrierGate.setParking_id("666");
      barrierGate.setGate_type(1);
      barrierGate.setGate_name("道闸1");
      barrierGate.setGate_model("01-1");
      barrierGate.setGate_code("编号");
      barrierGate.setGate_brand("品牌");
      BarrierGate p=barrierGateService.save(barrierGate);
      System.out.println(p);
  }

  @Test
  public void savecarinfo() throws Exception{
      CarInfo carInfo=new CarInfo();
      carInfo.setId("765");
      carInfo.setUser_sex(1);
      carInfo.setUser_name("王五");
      carInfo.setUser_mobile("13867896757");
      carInfo.setParking_id("666");
      carInfo.setCard_number("43567897547896493");
      carInfo.setCar_status(0);
      carInfo.setCar_no_color(1);
      carInfo.setCar_no_card(1);
      carInfo.setCar_model("型号");
      carInfo.setCar_brand("品牌");
      carInfo.setRecord_date("2018-7-11 20:20:20");
      carInfo.setCar_no("粤B87683");
      CarInfo p=carInfoService.save(carInfo);
      System.out.println(p);
  }

  @Test
  public void savechargedata() throws Exception{
      ChargeData chargeData=new ChargeData();
      chargeData.setId("2883");
      chargeData.setCar_id("098");
      chargeData.setParking_id("666");
      chargeData.setYs_money(50d);
      chargeData.setSs_money(50d);
      chargeData.setPay_type(1);
      chargeData.setPay_terminal("android");
      chargeData.setMonth_num(3);
      chargeData.setFix_card_value(2);
      chargeData.setBillcharge_end("2018-07-16 20:20:20");
      chargeData.setBillcharge_start("2018-07-12 20:20:20");
      chargeData.setCharge_date("2018-07-12 20:20:20");
      ChargeData p=chargeDateService.save(chargeData);
      System.out.println(p);
  }

  @Test
  public void saveinparking(){
      InParking inParking=new InParking();
      inParking.setId("098");
      inParking.setParking_id("666");
      inParking.setGate_id("01");
      inParking.setRemain_num(60);
      inParking.setRemain_fix_num(36);
      inParking.setParking_type(2);
      inParking.setIn_photo("入场照片");
      inParking.setIn_note("特殊说明");
      inParking.setIc_card_info("06433");
      inParking.setCar_no_color(1);
      inParking.setCar_no_card(1);
      inParking.setCar_model("型号");
      inParking.setCar_brand("品牌");
      inParking.setFix_card_value(4);
      inParking.setIn_time("2018-7-11 13:14:15");
      inParking.setOpen_mode(1);
      inParking.setCar_no("粤B679434");
      InParking p=inParkingService.save(inParking);
      System.out.println(p);
  }

  @Test
  public void saveoutparking(){
      OutParking outParking=new OutParking();
      outParking.setId("098");
      outParking.setParking_id("666");
      outParking.setYs_money(50d);
      outParking.setSs_money(50d);
      outParking.setPay_type(1);
      outParking.setPay_terminal("android");
      outParking.setOut_photo("出场照片");
      outParking.setOpen_note("特殊业务");
      outParking.setIn_parking_id("01");
      outParking.setIc_card_info("06433");
      outParking.setGate_id("02");
      outParking.setCar_no("粤B679434");
      outParking.setOut_time("2018-7-13 13:14:15");
      outParking.setOpen_mode(1);
      outParking.setParking_time(87557);
      OutParking p=outParkingService.save(outParking);
      System.out.println(p);
  }


@Test
public void findProductInstance() throws Exception{
	ParkingLot parkingLot = new ParkingLot();
	parkingLot.setProject_code("6789");
	ParkingLot p =parkingLotService.findEntityOne(parkingLot);
	System.out.println("返回数据："+p);
	
}

@Test
public void findCount(){
      ParkingLot parkingLot=new ParkingLot();
      parkingLot.setProject_code("FEDBA01C-0B26-42DA-BF04-4BE5FD9BAB23");
      ExampleMatcher matcher = ExampleMatcher.matching();
      Example<ParkingLot> example = Example.of(parkingLot,matcher);
      long a=parkingLotService.findExampleCount(example);
      System.out.println(a);
}

@Test
public void findBarrierGate() throws Exception{
      BarrierGate barrierGate=new BarrierGate();
      barrierGate.setGate_name("道闸1");
      BarrierGate p=barrierGateService.findEntityOne(barrierGate);
      System.out.println(p);
}
	
//@Test
//public void findPage() throws Exception{
//	ParkingLot parkingLot = new ParkingLot();
//	parkingLot.setProduct_id("1607d2b63f5f00011607d2b63f5f9401");
//	
//	List<ParkingLot> list = parkingLotService.findAll(parkingLot, null);
//	
////	Pageable pageable = new PageRequest(1,10,null);
////	SpringDataPageable pageable = new SpringDataPageable();
////	List<ProductInstance> pInstance = new ArrayList<ProductInstance>();
////	pInstance.add(new ProductInstance(Direction.DESC, new ProductInstance(Direction.DESC, "createTime")));
////	pageable.setSort(new Sort(orders));
//	//每页显示条数
////	pageable.setPagesize(10);
//////当前页
////	pageable.setPagenumber(2);
//	PageRequest pageRequest = new PageRequest();
//	pageRequest.setCurrent_page(1);
//	pageRequest.setPer_page(10);
//
//	PageResponse<ParkingLot> page =parkingLotService.findAllPage(parkingLot, pageRequest);
//	System.out.println("返回example数据："+list);
//	
//	System.out.println(page.getTotal());
//System.out.println(page.getClass());
//	System.out.println("返回example数据："+page.getList());
//	
//}

}
