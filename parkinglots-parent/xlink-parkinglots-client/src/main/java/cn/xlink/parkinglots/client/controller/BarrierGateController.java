package cn.xlink.parkinglots.client.controller;


import cn.xlink.parkinglots.api.domain.*;
import cn.xlink.parkinglots.api.exception.ErrorCode;
import cn.xlink.parkinglots.api.page.PageResponse;
import cn.xlink.parkinglots.api.response.Response;
import cn.xlink.parkinglots.api.response.ResponseUtil;
import cn.xlink.parkinglots.api.service.*;
import cn.xlink.parkinglots.client.permission.AccessToken;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/***
 * 道闸信息 3.1-3.3
 * */
@Controller
@RequestMapping("/v2/parks")
public class BarrierGateController {

    static final Logger logger = LogManager.getLogger(BarrierGateController.class);

    @Reference
    BarrierGateService barrierGateService;

    @Reference
    BarrierGateLatestStatusService barrierGateLatestStatusService;
    @Reference
    OutParkingService outParkingService;
    @Reference
    InParkingService inParkingService;
    @Reference
    ParkingLotService parkingLotService;

    //3.1.停车场道闸列表 分页
    @RequestMapping(value = "/parkinglots/gate/{parking_id}/all_list", method = RequestMethod.POST)
    @ResponseBody
    public Response<?> findGateList(HttpServletRequest request,
                                    @PathVariable("parking_id") String parking_id,
                                    @RequestBody BarrierGatePage barrierGatePage) {
//        AccessToken accessToken = (AccessToken) request.getAttribute("token");
//        String token = accessToken.getAccessToken();
        BarrierGate barrierGate = new BarrierGate();
        barrierGate.setParking_id(parking_id);
        PageResponse<BarrierGate> allPage = barrierGateService.findAllPage(barrierGate, barrierGatePage.getPageRequest());
        for (int i = 0; i < allPage.getList().size(); i++) {
            String gate_id = allPage.getList().get(i).getId();
            //根据道闸id查询道闸状态表
            BarrierGateLatestStatus barrierGateLatestStatus = new BarrierGateLatestStatus();
            barrierGateLatestStatus.setGate_id(gate_id);
            BarrierGateLatestStatus entityOne = barrierGateLatestStatusService.findEntityOne(barrierGateLatestStatus);
            if (entityOne != null) {
                allPage.getList().get(i).setBarrierGateLatestStatus(entityOne);
            } else {
                Response<Void> response = ResponseUtil.bizError("11010", "查询的道闸状态不存在");
                response.setStatus(500);
                return response;
            }

        }
        return ResponseUtil.success(allPage);
    }

    //3.4获取停车场所有道闸
    @RequestMapping(value = "/parkinglots/gate/{parking_id}/list", method = RequestMethod.GET)
    @ResponseBody
    public Response<?> findAllGate(HttpServletRequest request,
                                   @PathVariable("parking_id") String parking_id) {
//        AccessToken accessToken = (AccessToken) request.getAttribute("token");
//        String token = accessToken.getAccessToken();
        try {

            BarrierGate barrierGate = new BarrierGate();
            barrierGate.setParking_id(parking_id);
            List<BarrierGate> all = barrierGateService.findAll(barrierGate, null);
            if (all != null && all.size() > 0) {
                for (int i = 0; i < all.size(); i++) {
                    //获取道闸id
                    String gate_id = all.get(i).getId();
                    //根据道闸id查询道闸状态表
                    BarrierGateLatestStatus barrierGateLatestStatus = new BarrierGateLatestStatus();
                    barrierGateLatestStatus.setGate_id(gate_id);
                    BarrierGateLatestStatus entityOne = barrierGateLatestStatusService.findEntityOne(barrierGateLatestStatus);
                    if (entityOne != null) {
                        all.get(i).setBarrierGateLatestStatus(entityOne);
                    } else {
                        Response<Void> response = ResponseUtil.bizError("11010", "查询的道闸状态不存在");
                        response.setStatus(500);
                        return response;
                    }
                }
                return ResponseUtil.success(all);
            } else {
                Response<Void> response = ResponseUtil.bizError("11010", "查询对象不存在");
                response.setStatus(500);
                return response;
            }
        } catch (Exception e) {
            logger.error("查询实例列表异常：", e);
            Response<Void> resp = ResponseUtil.bizError(InstanceErrorCode.UNKNOW_ERROR);
            resp.setMsg(e.getMessage());
            return resp;
        }
    }

    //3.2.道闸门操作  开闸 关闸
//    @RequestMapping(value = "/parkinglot/gate/{gate_id}/operations", method = RequestMethod.POST)
//    @ResponseBody
//    public Response<?> operatorGate(HttpServletRequest request,
//                                    @PathVariable("gate_id") String gate_id,
//                                    BarrierGatePage barrierGatePage) {
//        AccessToken accessToken = (AccessToken) request.getAttribute("token");
//        String token = accessToken.getAccessToken();
//        try {
//            PageResponse<BarrierGate> page = barrierGateService.findAllPage(barrierGatePage.getBarrierGate(), barrierGatePage.getPageRequest());
//
//            return ResponseUtil.success(page);
//        } catch (Exception e) {
//            logger.error("查询实例列表异常：", e);
//            Response<Void> resp = ResponseUtil.bizError(InstanceErrorCode.UNKNOW_ERROR);
//            resp.setMsg(e.getMessage());
//            return resp;
//        }
//    }

    //3.3.停车场添加道闸
    @RequestMapping(value = "/parkinglots/gate/authorization/save", method = RequestMethod.POST)
    @ResponseBody
    public Response<?> addGate(HttpServletRequest request,
                               @RequestBody BarrierGate barrierGate) {
//        AccessToken accessToken = (AccessToken) request.getAttribute("token");
//        String token = accessToken.getAccessToken();
        String gate_id = barrierGate.getId();
        BarrierGate barrier = new BarrierGate();
        barrier.setId(gate_id);
        BarrierGate entityOne = barrierGateService.findEntityOne(barrier);
        if (entityOne == null) {
            BarrierGate gate = barrierGateService.save(barrierGate);
            return ResponseUtil.success(gate);
        } else {
            Response<Void> response = ResponseUtil.bizError("11010", "道闸id不能重复");
            response.setStatus(500);
            return response;
        }
    }

    //4.6 道闸流量统计
    @RequestMapping(value = "/parkinglots/gate/{project_id}/flow", method = RequestMethod.POST)
    @ResponseBody
    public Response<?> getGateFlow(HttpServletRequest request, @PathVariable("project_id") String project_id,
                                   @RequestBody BarrierGate barrierGate) {
        try {
            InParking inParking = new InParking();
            inParking.setProject_id(project_id);
            inParking.setStart_time(barrierGate.getStart_time());
            inParking.setEnd_time(barrierGate.getEnd_time());
            OutParking outParking = new OutParking();
            outParking.setProject_id(project_id);
            outParking.setStart_time(barrierGate.getStart_time());
            outParking.setEnd_time(barrierGate.getEnd_time());
            List<BarrierGate> retList = new ArrayList<>();
            List<BarrierGate> inoutList = new ArrayList<>();
            //进场道闸
            List<BarrierGate> inList = inParkingService.findOutFlowGroupBy(inParking);
            //出场道闸
            List<BarrierGate> outList = outParkingService.findOutFlowGroupBy(outParking);
            inoutList.addAll(inList);
            inoutList.addAll(outList);
            for (BarrierGate barrierGate1 : inoutList) {
                Integer temporary_count;
                //查询道闸临停车流量
                Integer gate_type = barrierGate1.getGate_type();
                if(gate_type.equals(0)){//进场道闸查进场日志
                    inParking.setFix_card_value(0);
                    inParking.setParking_id(barrierGate1.getParking_id());
                    inParking.setGate_id(barrierGate1.getId());
                    temporary_count = (int)inParkingService.count(inParking);
                }else{//出场道闸查出场日志
                    outParking.setFix_card_value(0);
                    outParking.setParking_id(barrierGate1.getParking_id());
                    outParking.setGate_id(barrierGate1.getId());
                    temporary_count = (int)outParkingService.count(outParking);
                }
                barrierGate1.setIc_temporary_count(temporary_count);
                //查询停车场名字
                ParkingLot ParkingLotExample = new ParkingLot();
                ParkingLotExample.setId(barrierGate1.getParking_id());
                ParkingLot parkingLot = parkingLotService.findEntityOne(ParkingLotExample);
                barrierGate1.setParkingLot(parkingLot);
                //补充道闸名字
                BarrierGate barrierGateExample = new BarrierGate();
                barrierGateExample.setId(barrierGate1.getId());
                BarrierGate barrierGate2 = barrierGateService.findEntityOne(barrierGateExample);
                barrierGate1.setGate_name(barrierGate2.getGate_name());
                barrierGate1.setGate_code(barrierGate2.getGate_code());
                retList.add(barrierGate1);
            }
            return ResponseUtil.success(retList);
        } catch (Exception e) {
            logger.error("查询实例列表异常：", e);
            Response<Void> resp = ResponseUtil.bizError(InstanceErrorCode.UNKNOW_ERROR);
            resp.setMsg(e.getMessage());
            return resp;
        }
    }


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