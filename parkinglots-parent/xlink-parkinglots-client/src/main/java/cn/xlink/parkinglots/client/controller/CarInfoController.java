package cn.xlink.parkinglots.client.controller;

import cn.xlink.parkinglots.api.domain.CarInfo;
import cn.xlink.parkinglots.api.domain.CarInfoPage;
import cn.xlink.parkinglots.api.domain.ChargeData;
import cn.xlink.parkinglots.api.exception.ErrorCode;
import cn.xlink.parkinglots.api.page.PageRequest;
import cn.xlink.parkinglots.api.page.PageResponse;
import cn.xlink.parkinglots.api.response.Response;
import cn.xlink.parkinglots.api.response.ResponseUtil;
import cn.xlink.parkinglots.api.service.CarInfoService;
import cn.xlink.parkinglots.api.service.ChargeDateService;
import cn.xlink.parkinglots.api.util.TimeUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/v2/parks")
public class CarInfoController {
    static final Logger logger = LogManager.getLogger(ParkingLotsController.class);
    @Reference
    private CarInfoService carInfoService;
    @Reference
    private ChargeDateService chargeDateService;

    /**查询车辆登记信息*/
    @RequestMapping(value = "/parkinglots/vehicle_registration", method = RequestMethod.POST)
    @ResponseBody
    public Response<?> findCarInfoList(HttpServletRequest request,
                                       @RequestBody CarInfoPage carInfoPage){
        try {
            long time = 0l;
            ChargeData chargeData = new ChargeData();
            PageRequest pageRequest = new PageRequest();
            if(carInfoPage.getPageRequest()==null){
                pageRequest.setPer_page(10);
                pageRequest.setCurrent_page(1);
            }else {
                if(pageRequest.getPer_page()==0){
                    pageRequest.setPer_page(10);
                    pageRequest.setCurrent_page(carInfoPage.getPageRequest().getCurrent_page());
                }else {
                    pageRequest=carInfoPage.getPageRequest();
                }

            }
            List<CarInfo> carInfoListResult=new ArrayList<>();
            //查询所有车辆登记信息
            PageResponse<CarInfo> page = carInfoService.findAllPage(carInfoPage.getCarInfo(), pageRequest);
            List<CarInfo> carInfoList = page.getList();
            for (CarInfo carinfo : carInfoList) {
                if(carInfoPage.getChargeData()!=null){
                    chargeData=carInfoPage.getChargeData();
                    chargeData.setCar_id(carinfo.getId());
                }else {
                    chargeData.setCar_id(carinfo.getId());
                }
                //根据车辆id查询车辆固定卡缴费信息
                List<ChargeData> chargeDataList = chargeDateService.findAll(chargeData, null);
                for (ChargeData chargedata : chargeDataList) {
                    if (time < TimeUtil.getNumerical(chargedata.getCharge_date()));
                    time = TimeUtil.getNumerical(chargedata.getCharge_date());
                }
                ChargeData chargeData1 = new ChargeData();
                chargeData1.setCharge_date(TimeUtil.getstring(time));
                chargeData1 = chargeDateService.findEntityOne(chargeData1);
                carinfo.setChargeData(chargeData1);
                if(chargeData1!=null){
                    carinfo.setRecently_delivery_money(chargeData1.getSs_money());
                }
                if(chargeDataList.size()>0){
                    carInfoListResult.add(carinfo);
                }
            }
            page.setList(carInfoListResult);
            return ResponseUtil.success(page);
        }catch (Exception e) {
            logger.error("查询实例列表异常：", e);
            Response<Void> resp = ResponseUtil.bizError(CarInfoController.InstanceErrorCode.UNKNOW_ERROR);
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
