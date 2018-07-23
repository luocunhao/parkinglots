package cn.xlink.parkinglots.client.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.xlink.parkinglots.api.domain.OutParking;
import cn.xlink.parkinglots.api.domain.ParkingLotLatestSpace;
import cn.xlink.parkinglots.api.service.OutParkingService;
import cn.xlink.parkinglots.api.service.ParkingLotLatestSpaceService;
import cn.xlink.parkinglots.api.util.TimeUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.xlink.parkinglots.api.domain.ParkingLot;
import cn.xlink.parkinglots.api.domain.ParkingLotPage;
import cn.xlink.parkinglots.api.exception.ErrorCode;
import cn.xlink.parkinglots.api.page.PageRequest;
import cn.xlink.parkinglots.api.page.PageResponse;
import cn.xlink.parkinglots.api.response.Response;
import cn.xlink.parkinglots.api.response.ResponseUtil;
import cn.xlink.parkinglots.api.service.ParkingLotService;
import cn.xlink.parkinglots.client.permission.AccessToken;


@Controller
@RequestMapping("/v2/parks")
public class ParkingLotDemoController {

    static final Logger logger = LogManager.getLogger(ParkingLotDemoController.class);
    @Reference
    private ParkingLotService parkingLotService;
    @Reference
    private OutParkingService outParkingService;
    @Reference
    private ParkingLotLatestSpaceService parkingLotLatestSpaceService;

	/**
	 * 重构单个查询
	 **/
    @RequestMapping(value = "/parkinglots/{id}",method = RequestMethod.POST)
    @ResponseBody
    public Response<?> ParkingLotIdMesg(HttpServletRequest request,
                                         @PathVariable("id") String projectcode,@RequestBody ParkingLot parkingLot){
    	
    	System.out.println(request.getAttribute("token"));
    	AccessToken accessToken = (AccessToken) request.getAttribute("token");
        String token = accessToken.getAccessToken();
        PageRequest pageRequest=new PageRequest();
    	parkingLot.setProject_code(projectcode);
        PageResponse<ParkingLot> info = parkingLotService.findAllPage(parkingLot,pageRequest);
        
        return ResponseUtil.success(info);
    }   
   

    /**
	 * 重构当个查询 @RequestBody ParkingLot parkingLot
	 **/
    @RequestMapping(value = "/parkinglots/save",method = RequestMethod.POST)
    @ResponseBody
    public Response<?> ParkingLotIdMesg(HttpServletRequest request,@RequestBody ParkingLot parkingLot){
    	AccessToken accessToken = (AccessToken) request.getAttribute("token");
        String token = accessToken.getAccessToken();
        
        ParkingLot info = parkingLotService.save(parkingLot);
        return ResponseUtil.success(info);
    }
    
    //列表
    @ResponseBody
    @RequestMapping(value = "/parkinglots/list", method = RequestMethod.POST)
    public Response<?> listProductInstance(HttpServletRequest request,
                                               @RequestBody ParkingLot parkingLot) {
    	AccessToken accessToken = (AccessToken) request.getAttribute("token");
        String token = accessToken.getAccessToken();
       
        List<ParkingLot> infoList = parkingLotService.findAll(parkingLot, null);
//        OutParking outParking=new OutParking();
//        outParking.setParking_id("666");
//        List<OutParking> list=outParkingService.findAll(outParking,null);
        
        return ResponseUtil.success(infoList);
    } 
    
  /** 分页查询停车场列表*/
    @ResponseBody
    @RequestMapping(value = "/parkinglots/page",method = RequestMethod.POST)
    public Response<?> pageProductInstance(HttpServletRequest request , @RequestBody ParkingLotPage parkingLotPage) {
        try {
        	PageRequest pageRequest = new PageRequest();
        	pageRequest=parkingLotPage.getPageRequest();
            if(pageRequest.getPer_page()==0){
                pageRequest.setPer_page(10);
            }
            Double today_charge=0d,month_charge=0d;
//            OutParking outParking=new OutParking();
            ParkingLot parkingLot=new ParkingLot();
            parkingLot.setProject_code(parkingLotPage.getParkingLot().getProject_code());
            ParkingLotLatestSpace parkingLotLatestSpace=new ParkingLotLatestSpace();
        	PageResponse<ParkingLot> page = parkingLotService.findAllPage(parkingLotPage.getParkingLot(), pageRequest);
//        	List<ParkingLot> parkingLotList=page.getList();
//            for (ParkingLot park:parkingLotList) {
//                OutParking outParking=new OutParking();
//                outParking.setParking_id(park.getParking_id());
//                //根据停车场id查询出场记录
//                List<OutParking> outParkingList=outParkingService.findAll(outParking,null);
//                if(outParkingList.size()!=0){
//                    for (OutParking outPark:outParkingList) {
//                        //计算当天收费总额
//                        if(TimeUtil.getNumerical(outPark.getOut_time())>TimeUtil.putNowToday()){
//                            today_charge+=outPark.getSs_money();
//                        }
//                        //计算当月收费总额
//                        if(TimeUtil.getNumerical(outPark.getOut_time())>TimeUtil.putNowMonth()){
//                            month_charge+=outPark.getSs_money();
//                        }
//                    }
//                }
//                park.setMonth_charge(month_charge);
//                park.setToday_charge(today_charge);
//                //查询车位信息
//                String s=park.getParking_id();
//                parkingLotLatestSpace.setParking_id(park.getParking_id());
//                parkingLotLatestSpace=parkingLotLatestSpaceService.findEntityOne(parkingLotLatestSpace);
//                if(parkingLotLatestSpace!=null){
//                    park.setParkingLotLatestSpace(parkingLotLatestSpace);
//                }
//            }
            return ResponseUtil.success(page);
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
