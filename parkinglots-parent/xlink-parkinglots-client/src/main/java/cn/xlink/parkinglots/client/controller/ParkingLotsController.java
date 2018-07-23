package cn.xlink.parkinglots.client.controller;

import cn.xlink.parkinglots.api.domain.*;
import cn.xlink.parkinglots.api.exception.ErrorCode;
import cn.xlink.parkinglots.api.page.PageRequest;
import cn.xlink.parkinglots.api.page.PageResponse;
import cn.xlink.parkinglots.api.response.Response;
import cn.xlink.parkinglots.api.response.ResponseUtil;
import cn.xlink.parkinglots.api.service.*;
import cn.xlink.parkinglots.api.util.TimeUtil;
import cn.xlink.parkinglots.client.permission.AccessToken;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.*;

/**
 * 停车场管理 5.1-5.3
 */

@Controller
@RequestMapping("/v2/parks")
public class ParkingLotsController {
    static final Logger logger = LogManager.getLogger(ParkingLotsController.class);
    @Reference
    ParkingLotManageService parkingLotManageService;
    @Reference
    ParkingLotService parkingLotService;
    @Reference
    OutParkingService outParkingService;
    @Reference
    ParkingLotLatestSpaceService parkingLotLatestSpaceService;
    @Reference
    BarrierGateService barrierGateService;
    @Reference
    BarrierGateLatestStatusService barrierGateLatestStatusService;

    //5.1添加停车场实例
    @RequestMapping(value = "/parkinglots/{project_id}/save", method = RequestMethod.POST)
    @ResponseBody
    public Response<?> addParking(HttpServletRequest request,
                                  @PathVariable("project_id") String project_id,
                                  @RequestBody ParkingLot parkingLot){
//        AccessToken accessToken = (AccessToken) request.getAttribute("token");
//        String token = accessToken.getAccessToken();
        try {
            parkingLot.setProject_code(project_id);
            String id = parkingLot.getId();
            ParkingLot palot = new ParkingLot();
            palot.setId(id);
            ParkingLot p = parkingLotManageService.findEntityOne(palot);
            if (p == null) {
                ParkingLot pa = parkingLotManageService.save(parkingLot);
                return ResponseUtil.success(pa);
            } else {
                Response<Void> res = ResponseUtil.bizError("11010", "id不能重复");
                res.setStatus(500);
                return res;
            }
        } catch (Exception e) {
            logger.error("id不能重复：", e);
            Response<Void> resp = ResponseUtil.bizError(InstanceErrorCode.UNKNOW_ERROR);
            resp.setMsg(e.getMessage());
            return resp;
        }

    }

    //5.2删除停车场
    @RequestMapping(value = "/parkinglots/{parking_id}/delete",method = RequestMethod.DELETE)
    @ResponseBody
    public Response<?> deleteParking(HttpServletRequest request,
                                     @PathVariable("parking_id") String parking_id){
//        AccessToken accessToken = (AccessToken) request.getAttribute("token");
//        String token = accessToken.getAccessToken();
        ParkingLot parking = new ParkingLot();
        parking.setId(parking_id);
        ParkingLot entityOne = parkingLotManageService.findEntityOne(parking);
        if (entityOne!=null) {
            parkingLotManageService.delete(parking);
            return ResponseUtil.success(parking);

        }else {
            Response<Void> re = ResponseUtil.bizError("500", "删除对象不存在");
            re.setStatus(500);
            return re;
        }
    }

    //5.3查询停车场实例
    @RequestMapping(value = "/parkinglots/{project_id}/msg",method = RequestMethod.POST)
    @ResponseBody
    public Response<?> getParkingImfo(HttpServletRequest request,
                                     @PathVariable("project_id") String project_id,
                                      @RequestBody ParkingLot parkingLot) throws ParseException {
//        AccessToken accessToken = (AccessToken) request.getAttribute("token");
//        String token = accessToken.getAccessToken();
        parkingLot.setProject_code(project_id);
        List<ParkingLot> lotList = parkingLotService.findAll(parkingLot, null);

        Map<String,ParkingLot> mp = new HashMap<>();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < lotList.size(); i++) {
            String create_date = lotList.get(i).getCreate_date();
            ParkingLot p1 = lotList.get(i);
            mp.put(create_date,p1);
            list.add(create_date);
        }
        //按照登记日期倒序排列
        JSONObject data = new JSONObject();
        List<ParkingLot> l = new ArrayList<>();
        List<String> timeOder = TimeUtil.sortListDesc(list);
        for (int i = 0; i < timeOder.size(); i++) {
            ParkingLot p2 = mp.get(timeOder.get(i));
            ParkingLot p = new ParkingLot();
            p.setCreate_date(timeOder.get(i));
            l.add(p2);
        }

        return ResponseUtil.success(l);
    }



    /**
     * 分页查询停车场列表
     */
    @ResponseBody
    @RequestMapping(value = "/parkinglots/message/list",method = RequestMethod.POST)
    public Response<?> pageProductInstance(HttpServletRequest request , @RequestBody ParkingLotPage parkingLotPage) {
        try {
//            ExampleMatcher matcher = ExampleMatcher.matching();
//            Example<ParkingLot> example = Example.of(parkingLotPage.getParkingLot(),matcher);
//            long count=parkingLotService.findExampleCount(example);
            PageRequest pageRequest = new PageRequest();
            if(parkingLotPage.getPageRequest()==null){
                pageRequest.setPer_page(10);
                pageRequest.setCurrent_page(1);
            }else {
                if(pageRequest.getPer_page()==0){
                    pageRequest.setPer_page(10);
                    pageRequest.setCurrent_page(parkingLotPage.getPageRequest().getCurrent_page());
                }else {
                    pageRequest=parkingLotPage.getPageRequest();
                }

            }
            Double today_charge=0d,month_charge=0d;
//            OutParking outParking=new OutParking();
            ParkingLot parkingLot=new ParkingLot();
            parkingLot.setProject_code(parkingLotPage.getParkingLot().getProject_code());

            PageResponse<ParkingLot> page = parkingLotService.findAllPage(parkingLotPage.getParkingLot(), pageRequest);
            List<ParkingLot> parkingLotList=page.getList();
            for (ParkingLot park:parkingLotList) {
                OutParking outParking=new OutParking();
                outParking.setParking_id(park.getId());
                //根据停车场id查询出场记录
                List<OutParking> outParkingList=outParkingService.findAll(outParking,null);
                if(outParkingList.size()!=0){
                    for (OutParking outPark:outParkingList) {
                        //计算当天收费总额
                        if(TimeUtil.getNumerical(outPark.getOut_time())>TimeUtil.putNowToday()){
                            today_charge+=outPark.getSs_money();
                        }
                        //计算当月收费总额
                        if(TimeUtil.getNumerical(outPark.getOut_time())>TimeUtil.putNowMonth(0)){
                            month_charge+=outPark.getSs_money();
                        }
                    }
                }
                park.setMonth_charge(month_charge);
                park.setToday_charge(today_charge);
                //查询车位信息
                ParkingLotLatestSpace parkingLotLatestSpace=new ParkingLotLatestSpace();
                parkingLotLatestSpace.setParking_id(park.getId());
                parkingLotLatestSpace=parkingLotLatestSpaceService.findEntityOne(parkingLotLatestSpace);
                if(parkingLotLatestSpace!=null){
                    park.setParkingLotLatestSpace(parkingLotLatestSpace);
                }
            }
            page.setList(parkingLotList);
            return ResponseUtil.success(page);
        } catch (Exception e) {
            logger.error("查询实例列表异常：", e);

            Response<Void> resp = ResponseUtil.bizError(ParkingLotsController.InstanceErrorCode.UNKNOW_ERROR);
            resp.setMsg(e.getMessage());
            resp.setStatus(500);
            return resp;
        }
    }

    /**项目下所有停车场信息统计*/
    @ResponseBody
    @RequestMapping(value = "/parkinglots/{project_id}/statistics",method = RequestMethod.GET)
    public Response<?> findParkingLotsStatistics(HttpServletRequest request,
                                                @PathVariable("project_id") String project_code){
        try {
//            AccessToken accessToken = (AccessToken) request.getAttribute("token");
//            String token = accessToken.getAccessToken();
            int gate_number = 0,//道闸总数
                    all_book_space_number = 0,//停车位总数
                    rest_book_space_number = 0,//空闲车位总数
                    gate_is_online_number = 0;//在线道闸数
            double today_charge = 0d;
            ParkingLot parkingLot = new ParkingLot();//停车场对象


//            BarrierGateLatestStatus barrierGateLatestStatus = new BarrierGateLatestStatus();//道闸-实时状态对象

            parkingLot.setProject_code(project_code);
            List<BarrierGate> barrierGateList = null;
            List<ParkingLot> parkingLotList = parkingLotService.findAll(parkingLot, null);
            for (ParkingLot park : parkingLotList) {
                if(park.getIn_park_mount()!=null) {
                    gate_number += park.getIn_park_mount();
                }
                if(park.getOut_park_mount()!=null){
                    gate_number += park.getOut_park_mount();
                }
                if(park.getAll_book_space()!=null){
                    all_book_space_number = park.getAll_book_space();
                }
                ParkingLotLatestSpace parkingLotLatestSpace = new ParkingLotLatestSpace();//车场信息-实时车位对象
                parkingLotLatestSpace.setParking_id(park.getId());
                //查询停车场空闲车位数
                parkingLotLatestSpace = parkingLotLatestSpaceService.findEntityOne(parkingLotLatestSpace);
                if (parkingLotLatestSpace != null) {
                    rest_book_space_number += parkingLotLatestSpace.getRest_book_space();
                }
                //查询停车场下的道闸
                BarrierGate barrierGate = new BarrierGate();//道闸对象
                barrierGate.setParking_id(park.getId());
                barrierGateList = barrierGateService.findAll(barrierGate, null);
                for (BarrierGate gate : barrierGateList) {
                    BarrierGateLatestStatus barrierGateLatestStatus=new BarrierGateLatestStatus();
                    barrierGateLatestStatus.setGate_id(gate.getId());
                    //查询道闸是否在线
                    barrierGateLatestStatus = barrierGateLatestStatusService.findEntityOne(barrierGateLatestStatus);
                    if (barrierGateLatestStatus != null) {
                        if (barrierGateLatestStatus.getIs_online() == 1) {
                            gate_is_online_number++;
                        }
                    }
                }
                //查询出场记录中的缴费
                OutParking outParking = new OutParking();//出场记录对象
                outParking.setParking_id(park.getId());
                List<OutParking> outParkingList = outParkingService.findAll(outParking, null);
                if (outParkingList.size() != 0) {
                    for (OutParking outPark : outParkingList) {
                        //计算当天收费总额
                        if (TimeUtil.getNumerical(outPark.getOut_time()) > TimeUtil.putNowToday()) {
                            today_charge += outPark.getSs_money();
                        }
                    }
                }
            }
            parkingLot.setToday_charge_number(today_charge);
            parkingLot.setGate_is_online_number(gate_is_online_number);
            parkingLot.setRest_book_space_number(rest_book_space_number);
            parkingLot.setAll_book_space_number(all_book_space_number);
            parkingLot.setGate_number(gate_number);
            parkingLot.setParkingLot_number(parkingLotList.size());

            return ResponseUtil.success(parkingLot);
        } catch (Exception e) {
            logger.error("查询实例列表异常：", e);

            Response<Void> resp = ResponseUtil.bizError(ParkingLotsController.InstanceErrorCode.UNKNOW_ERROR);
            resp.setMsg(e.getMessage());
            resp.setStatus(404);
            return resp;
        }
    }

    //4.5项目下停车场信息统计
    @ResponseBody
    @RequestMapping(value = "/parkinglots/{project_id}/payment/statistics", method = RequestMethod.POST)
    public Response<?> findParkingLotPayStatistics(@PathVariable("project_id") String project_id, @RequestBody OutParking outParking) {
        try {
            Double fix_money = 0.0;
            Double temporary_ss_money = 0.0;
            Double temporary_ys_money = 0.0;
            Double payment_completion;
            outParking.setProject_id(project_id);
            //收费方式统计
            ParkingLot parkingLot =
                    outParkingService.findGroupByPayType(outParking);
            //统计临停卡 fix_card_card_value = 0
            outParking.setFix_card_value(0);
            List<OutParking> outParkings0 =
                    outParkingService.findListByProjectIdAndFixCardValue(outParking);
            for (OutParking outParking0 : outParkings0) {
                temporary_ss_money += outParking0.getSs_money();
                temporary_ys_money += outParking0.getYs_money();
            }
            //统计临停的应收完成率
            if(temporary_ss_money.equals(temporary_ys_money)){
                payment_completion = 1.0;
            }else{
                payment_completion = temporary_ss_money / temporary_ys_money;
            }
            //统计固定卡 fix_card_value != 0
            List<OutParking> outParkingsFix =
                    outParkingService.findListByFixCardValueNeq0(outParking);
            for (OutParking outParkingFix : outParkingsFix) {
                fix_money += outParkingFix.getSs_money();
            }
            parkingLot.setTemporary_money(temporary_ss_money);
            parkingLot.setFix_money(fix_money);
            parkingLot.setPayment_completion(payment_completion);
            return ResponseUtil.success(parkingLot);
        } catch (Exception e) {
            logger.error("查询实例列表异常：", e);
            Response<Void> resp = ResponseUtil.bizError(ParkingLotsController.InstanceErrorCode.UNKNOW_ERROR);
            resp.setMsg(e.getMessage());
            resp.setStatus(404);
            return resp;
        }
    }


    //停车场最近半年固定卡停车位空闲统计



    private static enum InstanceErrorCode implements ErrorCode {
        UNKNOW_ERROR("UNKNOW_ERROR", "未知错误"),
        CURRENT_MERCHANT_NONEXISTENT("CURRENT_MERCHANT_NONEXISTENT", "当前商户不存在");
        private String code;
        private String desc;

        InstanceErrorCode(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        @Override
        public String getCode() {
            return code;
        }

        @Override
        public String getDesc() {
            return desc;
        }

    }
}
