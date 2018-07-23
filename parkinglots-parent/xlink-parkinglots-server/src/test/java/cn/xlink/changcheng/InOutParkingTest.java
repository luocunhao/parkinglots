package cn.xlink.changcheng;

import cn.xlink.ParkinglotsServer;
import cn.xlink.parkinglots.api.domain.BarrierGate;
import cn.xlink.parkinglots.api.domain.InParking;
import cn.xlink.parkinglots.api.domain.OutParking;
import cn.xlink.parkinglots.api.domain.ParkingLot;
import cn.xlink.parkinglots.api.service.InParkingService;
import cn.xlink.parkinglots.api.service.OutParkingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @Author luoch
 * @Date 2018-07-18 09:08
 * @Desc
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ParkinglotsServer.class)
public class InOutParkingTest {
    @Autowired
    private OutParkingService outParkingService;
    @Autowired
    private InParkingService inParkingService;
    @Test
    public void test(){
        OutParking outParking = new OutParking();
        outParking.setProject_id("pro1");
        outParking.setStart_time("2017-06-06");
        outParking.setEnd_time("2019-01-01");
        ParkingLot parkingLot =
                outParkingService.findGroupByPayType(outParking);
        System.out.println(parkingLot);
    }
    @Test
    public void testGroup(){
        InParking inParking = new InParking();
        inParking.setStart_time("2017-06-06");
        inParking.setEnd_time("2019-01-01");
        inParking.setProject_id("pro1");
        List<BarrierGate> barrierGates =
                inParkingService.findOutFlowGroupBy(inParking);
        barrierGates.stream().forEach(x->{
            System.out.println(x.toString());
        });
    }
    @Test
    public void testGroup2(){
        OutParking outParking = new OutParking();
        outParking.setStart_time("2017-06-06");
        outParking.setEnd_time("2019-01-01");
        outParking.setProject_id("pro1");
        List<BarrierGate> barrierGates =
                outParkingService.findOutFlowGroupBy(outParking);
        barrierGates.stream().forEach(x->{
            System.out.println(x.toString());
        });
    }
    @Test
    public void testGroup3(){
        OutParking outParking = new OutParking();
        outParking.setStart_time("2017-06-06");
        outParking.setEnd_time("2019-01-01");
        outParking.setParking_id("666");
        System.out.println(
                outParkingService.findChargeGroupBy(outParking));
    }
    @Test
    public void testCount(){
        InParking inParking = new InParking();
        inParking.setStart_time("2017-06-06");
        inParking.setEnd_time("2019-01-01");
        inParking.setProject_id("pro1");
        inParking.setParking_id("666");
        inParking.setGate_id("01");
        inParking.setFix_card_value(4);
        System.out.println(inParkingService.count(inParking));
    }
    @Test
    public void testCount2(){
        OutParking outParking = new OutParking();
        outParking.setStart_time("2017-06-06");
        outParking.setEnd_time("2019-01-01");
        outParking.setProject_id("pro1");
        outParking.setParking_id("666");
        outParking.setGate_id("01");
        outParking.setFix_card_value(4);
        System.out.println(outParkingService.count(outParking));
    }
    @Test
    public void testMapReduce(){
        InParking inParking = new InParking();
        inParking.setStart_time("2017-06-06");
        inParking.setEnd_time("2019-01-01");
        inParking.setParking_id("666");
        System.out.println(inParkingService.inParkingMapReduce(inParking));
    }

    @Test
    public void testMapReduce2(){
        OutParking outParking = new OutParking();
        outParking.setStart_time("2017-06-06");
        outParking.setEnd_time("2019-01-01");
        outParking.setParking_id("666");
        System.out.println(outParkingService.mapReduce(outParking));
    }
}
