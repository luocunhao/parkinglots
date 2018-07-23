package cn.xlink.parkinglots.client.controller;

import cn.xlink.parkinglots.api.domain.*;
import cn.xlink.parkinglots.api.exception.ErrorCode;
import cn.xlink.parkinglots.api.page.PageRequest;
import cn.xlink.parkinglots.api.page.PageResponse;
import cn.xlink.parkinglots.api.response.Response;
import cn.xlink.parkinglots.api.response.ResponseUtil;
import cn.xlink.parkinglots.api.service.*;
import cn.xlink.parkinglots.api.util.TimeUtil;
import com.alibaba.fastjson.JSONArray;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author luoch
 * @Date 2018-07-11 17:34
 * @Desc 日志查询接口
 */
@Controller
@RequestMapping("/v2/parks")
public class InoutParkingController {
    static final Logger logger = LogManager.getLogger(InoutParkingController.class);
    @Reference
    InParkingService inParkingService;
    @Reference
    OutParkingService outParkingService;
    @Reference
    BarrierGateService barrierGateService;
    @Reference
    ChargeDateService chargeDateService;

    /**
     * 4.1进出场日志
     */
    @RequestMapping(value = "/inoutparking/page/{project_id}", method = RequestMethod.POST)
    @ResponseBody
    public Response<?> parkingLotLogInOut(@PathVariable("project_id") String project_id, @RequestBody InParkingPage inParkingPage) {
        try {
            //        System.out.println(request.getAttribute("token"));
//        AccessToken accessToken = (AccessToken) request.getAttribute("token");
//        String token = accessToken.getAccessToken();
            InParking inParkingExample = inParkingPage.getInParking();
            inParkingExample.setProject_id(project_id);
            PageResponse<InParking> page = inParkingService.findAllPage(inParkingExample, inParkingPage.getPageRequest());
            List<InParking> inParkings = page.getList();
            List<InParking> inParkingsResponse = new ArrayList<>();
            for (InParking li : inParkings) {
                //入场道闸对象
                String in_gate_id = li.getGate_id();
                BarrierGate inBarrierGateExample = new BarrierGate();
                inBarrierGateExample.setId(in_gate_id);
                inBarrierGateExample.setProject_id(project_id);
                BarrierGate inBarrierGate = barrierGateService.findEntityOne(inBarrierGateExample);
                //出场记录
                String in_event_id = li.getId();
                OutParking outParkingExample = new OutParking();
                outParkingExample.setIn_parking_id(in_event_id);
                outParkingExample.setProject_id(project_id);
                OutParking outParking = outParkingService.findEntityOne(outParkingExample);
                if (outParking != null) {
                    //出场道闸对象
                    String out_gate_id = outParking.getGate_id();
                    BarrierGate outBarrierGateExample = new BarrierGate();
                    outBarrierGateExample.setId(out_gate_id);
                    outBarrierGateExample.setProject_id(project_id);
                    BarrierGate outBarrierGate = barrierGateService.findEntityOne(outBarrierGateExample);
                    outParking.setBarrierGate(outBarrierGate);
                    li.setOutParking(outParking);
                }
                li.setBarrierGate(inBarrierGate);
                inParkingsResponse.add(li);
            }
            page.setList(inParkingsResponse);
            return ResponseUtil.success(page);
        } catch (Exception e) {
            logger.error("查询实例列表异常：", e);
            Response<Void> resp = ResponseUtil.bizError(InstanceErrorCode.UNKNOW_ERROR);
            resp.setMsg(e.getMessage());
            resp.setStatus(500);
            return resp;
        }
    }

    /**
     * 4.2进场日志
     **/
    @RequestMapping(value = "/inparking/page/{project_id}", method = RequestMethod.POST)
    @ResponseBody
    public Response<?> parkingLotLogIn(@PathVariable("project_id") String project_id, @RequestBody InParkingPage inParkingPage) {
        try {
            //        System.out.println(request.getAttribute("token"));
//        AccessToken accessToken = (AccessToken) request.getAttribute("token");
//        String token = accessToken.getAccessToken();
            InParking inParking = inParkingPage.getInParking();
            inParking.setProject_id(project_id);
            PageResponse<InParking> page = inParkingService.findAllPage(inParking, inParkingPage.getPageRequest());
            List<InParking> inParkings = page.getList();
            List<InParking> inParkingResponses = new ArrayList<>();
            for (InParking li : inParkings) {
                //根据进场日志的闸口ID查询闸口信息
                String gate_id = li.getGate_id();
                BarrierGate barrierGateExample = new BarrierGate();
                barrierGateExample.setId(gate_id);
                barrierGateExample.setProject_id(project_id);
                BarrierGate barrierGate =
                        barrierGateService.findEntityOne(barrierGateExample);
                //封装接口返回对象
                li.setBarrierGate(barrierGate);
                inParkingResponses.add(li);
            }
            //封装返回的page对象
            page.setList(inParkingResponses);
            return ResponseUtil.success(page);
        } catch (Exception e) {
            logger.error("查询实例列表异常：", e);
            Response<Void> resp = ResponseUtil.bizError(InstanceErrorCode.UNKNOW_ERROR);
            resp.setMsg(e.getMessage());
            resp.setStatus(500);
            return resp;
        }

    }

    /**
     * 4.3出场日志
     */
    @RequestMapping(value = "/outparking/page/{project_id}", method = RequestMethod.POST)
    @ResponseBody
    public Response<?> parkingLotLogOut(@PathVariable("project_id") String project_id, @RequestBody OutParkingPage outParkingPage) {
        try {
            //        System.out.println(request.getAttribute("token"));
//        AccessToken accessToken = (AccessToken) request.getAttribute("token");
//        String token = accessToken.getAccessToken();
            OutParking outParking = outParkingPage.getOutParking();
            outParking.setProject_id(project_id);
            PageResponse<OutParking> page = outParkingService.findAllPage(outParking, outParkingPage.getPageRequest());
            List<OutParking> outParkings = page.getList();
            List<OutParking> outParkingResponses = new ArrayList<>();
            for (OutParking li : outParkings) {
                //关联道闸
                String gate_id = li.getGate_id();
                BarrierGate barrierGateExample = new BarrierGate();
                barrierGateExample.setId(gate_id);
                barrierGateExample.setProject_id(project_id);
                BarrierGate barrierGate =
                        barrierGateService.findEntityOne(barrierGateExample);
                //封装接口返回对象
                li.setBarrierGate(barrierGate);
                outParkingResponses.add(li);
            }
            //封装返回的page对象
            page.setList(outParkingResponses);
            return ResponseUtil.success(page);
        } catch (Exception e) {
            logger.error("查询实例列表异常：", e);
            Response<Void> resp = ResponseUtil.bizError(InstanceErrorCode.UNKNOW_ERROR);
            resp.setMsg(e.getMessage());
            resp.setStatus(500);
            return resp;
        }
    }

    /**
     * 4.4
     * 固定卡缴费日志
     */
    @RequestMapping(value = "/chargelog/fixcard/page/{project_id}", method = RequestMethod.POST)
    @ResponseBody
    public Response<?> chargeLog(@PathVariable("project_id") String project_id, @RequestBody ChargeDataPage chargeDataPage) {
        try {
//            outParkingService.statistics("","","");
            //        System.out.println(request.getAttribute("token"));
//        AccessToken accessToken = (AccessToken) request.getAttribute("token");
//        String token = accessToken.getAccessToken();
            ChargeData chargeDataExample = chargeDataPage.getChargeData();
            chargeDataExample.setProject_id(project_id);
            PageResponse<ChargeData> page = chargeDateService.findAllPage(chargeDataExample,chargeDataPage.getPageRequest());
            return ResponseUtil.success(page);
        } catch (Exception e) {
            logger.error("查询实例列表异常：", e);
            Response<Void> resp = ResponseUtil.bizError(InstanceErrorCode.UNKNOW_ERROR);
            resp.setMsg(e.getMessage());
            resp.setStatus(500);
            return resp;
        }
    }


    /**
     * 2.1 停放车辆信息列表查询
     * */
    @RequestMapping(value = "/parkinglots/vehicle_information",method = RequestMethod.POST)
    @ResponseBody
    public Response<?> findParkingVehicles(HttpServletRequest request,
                                           @RequestBody InParkingPage inParkingPage){
        try {
            long park_time = 0;
            PageRequest pageRequest = new PageRequest();
            if (inParkingPage.getPageRequest() == null) {
                pageRequest.setPer_page(10);
                pageRequest.setCurrent_page(0);
            } else {
                if (pageRequest.getPer_page() == 0) {
                    pageRequest.setPer_page(10);
                    pageRequest.setCurrent_page(inParkingPage.getPageRequest().getCurrent_page());
                }else {
                    pageRequest=inParkingPage.getPageRequest();
                }

            }
            //查询进场车辆信息
            PageResponse<InParking> page = inParkingService.findAllPage(inParkingPage.getInParking(), pageRequest);
            List<InParking> inParkingList = page.getList();
            List<InParking> inParkingListResult = new ArrayList<>();
            for (InParking inParking : inParkingList) {
                OutParking outParking = new OutParking();
                outParking.setId(inParking.getId());
                outParking.setIn_parking_id("01");
                outParking = outParkingService.findEntityOne(outParking);
                if (outParking != null) {
                    park_time = (System.currentTimeMillis() / 1000) - (TimeUtil.getNumerical(inParking.getIn_time()));
                    inParking.setNow_parking_time(park_time);
                    BarrierGate barrierGate = new BarrierGate();
                    barrierGate.setId(inParking.getGate_id());
                    barrierGate = barrierGateService.findEntityOne(barrierGate);
                    inParking.setBarrierGate(barrierGate);
                    inParkingListResult.add(inParking);
                }

//           OutParking outParking=new OutParking();
//           outParking.setEvent_id(inParking.getEvent_id());
            }
            page.setList(inParkingListResult);
            return ResponseUtil.success(page);
        }catch (Exception e) {
            logger.error("查询实例列表异常：", e);
            Response<Void> resp = ResponseUtil.bizError(InstanceErrorCode.UNKNOW_ERROR);
            resp.setMsg(e.getMessage());
            resp.setStatus(500);
            return resp;
        }
    }

    //近24小时进场流量
    @RequestMapping(value = "/parkinglots/inparking/24hflow/{project_id}", method = RequestMethod.POST)
    @ResponseBody
    public Response<?> findInParking24hflow(HttpServletRequest request,
                                            @RequestBody InParking inParking) {
        try {
            JSONArray jsonArray = inParkingService.inParkingMapReduce(inParking);
            return ResponseUtil.success(jsonArray);
        } catch (Exception e) {
            logger.error("查询实例列表异常：", e);
            Response<Void> resp = ResponseUtil.bizError(InstanceErrorCode.UNKNOW_ERROR);
            resp.setMsg(e.getMessage());
            resp.setStatus(500);
            return resp;
        }
    }

    //近24小时出场流量
    @RequestMapping(value = "/parkinglots/outparking/24hflow/{project_id}", method = RequestMethod.POST)
    @ResponseBody
    public Response<?> findOutParking24hflow(HttpServletRequest request,
                                            @RequestBody OutParking outParking) {
        try {
            JSONArray jsonArray = outParkingService.mapReduce(outParking);
            return ResponseUtil.success(jsonArray);
        } catch (Exception e) {
            logger.error("查询实例列表异常：", e);
            Response<Void> resp = ResponseUtil.bizError(InstanceErrorCode.UNKNOW_ERROR);
            resp.setMsg(e.getMessage());
            resp.setStatus(500);
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
