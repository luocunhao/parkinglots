package cn.xlink.parkinglots.client.controller;

import cn.xlink.parkinglots.api.domain.ChargeData;
import cn.xlink.parkinglots.api.domain.OutParking;
import cn.xlink.parkinglots.api.domain.ParkingLot;
import cn.xlink.parkinglots.api.exception.ErrorCode;
import cn.xlink.parkinglots.api.response.Response;
import cn.xlink.parkinglots.api.response.ResponseUtil;
import cn.xlink.parkinglots.api.service.ChargeDateService;
import cn.xlink.parkinglots.api.service.OutParkingService;
import cn.xlink.parkinglots.api.service.ParkingLotService;
import cn.xlink.parkinglots.api.util.TimeUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/v2/parks")
public class ChargeDateController {
    static final Logger logger = LogManager.getLogger(ChargeDateController.class);

    @Reference
    private ChargeDateService chargeDateService;
    @Reference
    private ParkingLotService parkingLotService;
    @Reference
    private OutParkingService outParkingService;

    /**
     * 登记车辆统计
     * */
    @RequestMapping(value = "/parkinglots/{parking_id}/vehicle_registration",method = RequestMethod.GET)
    @ResponseBody
    public Response<?> findChargeDateSum(HttpServletRequest request,
                                         @PathVariable("parking_id") String parking_id){
        try {
            int ic_month = 0, ic_quarter = 0, ic_half_year = 0, ic_year = 0;
            double last_month_money = 0, this_month_money = 0;
            ChargeData chargeData = new ChargeData();
            chargeData.setParking_id(parking_id);
            List<ChargeData> chargeDataList = chargeDateService.findAll(chargeData, null);
            for (ChargeData chargedata : chargeDataList) {
                switch (chargedata.getFix_card_value()) {
                    case 1:
                        ic_month++;
                        break;
                    case 2:
                        ic_quarter++;
                        break;
                    case 3:
                        ic_half_year++;
                        break;
                    case 4:
                        ic_year++;
                        break;
                }
                if (TimeUtil.getNumerical(chargedata.getCharge_date()) < TimeUtil.putNowMonth(0) &&
                        TimeUtil.getNumerical(chargedata.getCharge_date()) * 1000 > TimeUtil.putNowMonth(-1)) {
                    last_month_money += chargedata.getSs_money();
                }
                if (TimeUtil.getNumerical(chargedata.getBillcharge_end()) > TimeUtil.putNowMonth(0) &&
                        TimeUtil.getNumerical(chargedata.getBillcharge_end()) * 1000 < TimeUtil.getNowMonthLastToday()) {
                    this_month_money += chargedata.getSs_money();

                }
            }
            chargeData.setLast_month_money(last_month_money);
            chargeData.setThis_month_money(this_month_money);
            chargeData.setIc_month(ic_month);
            chargeData.setIc_half_year(ic_half_year);
            chargeData.setIc_quarter(ic_quarter);
            chargeData.setIc_year(ic_year);
            chargeData.setRegister_car(chargeDataList.size());
            return ResponseUtil.success(chargeData);
        }catch (Exception e) {
            logger.error("查询实例列表异常：", e);
            Response<Void> resp = ResponseUtil.bizError(ChargeDateController.InstanceErrorCode.UNKNOW_ERROR);
            resp.setMsg(e.getMessage());
            resp.setStatus(404);
            return resp;
        }
    }


    /**
     * 缴费方式统计
     * */
    @RequestMapping(value = "/parkinglots/{prodect_code}/payment_methon",method = RequestMethod.GET)
    @ResponseBody
    public Response<?> findPaymentMethon(HttpServletRequest request,
                                         @PathVariable("product_code")String product_code){
        int alipay_year=0,cash_year=0,wechat_year=0,other_year=0;
        long time=System.currentTimeMillis()/1000-(365*24*360);
        ParkingLot parkingLot=new ParkingLot();
        parkingLot.setProject_code(product_code);
        //通过项目id查找所有停车场
        List<ParkingLot> parkingLotList=parkingLotService.findAll(parkingLot,null);
        for (ParkingLot parkinglot:parkingLotList) {
            OutParking outParking=new OutParking();
            outParking.setParking_id(parkingLot.getId());
            //听过停车场id查找所有出场记录
            List<OutParking> outParkingList=outParkingService.findAll(outParking,null);
            for (OutParking outparking:outParkingList) {
                if(TimeUtil.getNumerical(outParking.getOut_time())>=time){
                    switch (outparking.getPay_type()) {
                        case 0:
                            cash_year++;
                            break;
                        case 1:
                            wechat_year++;
                            break;
                        case 2:
                            alipay_year++;
                            break;
                        case 3:
                            other_year++;
                            break;
                    }
                }
            }
            ChargeData chargeData=new ChargeData();
            chargeData.setParking_id(parkingLot.getId());
            List<ChargeData> chargeDataList=chargeDateService.findAll(chargeData,null);
            for (ChargeData chargedata:chargeDataList) {
                if(TimeUtil.getNumerical(chargedata.getCharge_date())>=time){

                }
            }
        }
        return null;
    }


    /**
     * 卡套餐分布
     * */
    @RequestMapping(value = "/parkinglots/{project_code}/set_meal",method = RequestMethod.GET)
    @ResponseBody
    public Response<?> findIcSetMeal(HttpServletRequest request,
                                     @PathVariable("project_code") String project_id){
        try {


            int ic_day_sum = 0, ic_month_sum = 0, ic_quarter_sum = 0, ic_half_year_sum = 0, ic_year_sum = 0, ic_other_sum = 0;
            ChargeData chargeData = new ChargeData();
            ParkingLot parkingLot = new ParkingLot();
            parkingLot.setProject_code(project_id);
            List<ParkingLot> parkingLotList = parkingLotService.findAll(parkingLot, null);
            for (ParkingLot parkinglot : parkingLotList) {
                chargeData.setParking_id(parkinglot.getId());
                List<ChargeData> chargeDataList = chargeDateService.findAll(chargeData, null);
                for (ChargeData chargedate : chargeDataList) {
                    if (TimeUtil.getNumerical(chargedate.getBillcharge_end())*1000 > TimeUtil.putNowToday()) {
                        switch (chargedate.getFix_card_value()) {
                            case 0:
                                ic_day_sum++;
                                break;
                            case 1:
                                ic_month_sum++;
                                break;
                            case 2:
                                ic_quarter_sum++;
                                break;
                            case 3:
                                ic_half_year_sum++;
                                break;
                            case 4:
                                ic_year_sum++;
                                break;
                            case 5:
                                ic_other_sum++;
                                break;
                        }
                    }
                }
            }
            ChargeData chargeDataResult = new ChargeData();
            chargeDataResult.setIc_day_sum(ic_day_sum);
            chargeDataResult.setIc_month_sum(ic_month_sum);
            chargeDataResult.setIc_quarter_sum(ic_quarter_sum);
            chargeDataResult.setIc_year_sum(ic_year_sum);
            chargeDataResult.setIc_half_year_sum(ic_half_year_sum);
            chargeDataResult.setIc_other_sum(ic_other_sum);
            return ResponseUtil.success(chargeDataResult);
        }catch (Exception e) {
            logger.error("查询实例列表异常：", e);
            Response<Void> resp = ResponseUtil.bizError(ChargeDateController.InstanceErrorCode.UNKNOW_ERROR);
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
