package cn.xlink.parkinglots.client.controller;


import cn.xlink.parkinglots.api.domain.InParking;
import cn.xlink.parkinglots.api.domain.ParkingLot;
import cn.xlink.parkinglots.api.domain.ParkingLotLatestSpace;
import cn.xlink.parkinglots.api.exception.ErrorCode;
import cn.xlink.parkinglots.api.response.Response;
import cn.xlink.parkinglots.api.response.ResponseUtil;
import cn.xlink.parkinglots.api.service.InParkingService;
import cn.xlink.parkinglots.api.service.ParkingLotLatestSpaceService;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RequestMapping("/v2/parks")
@Controller
public class ParkingLotLatestSpaceController {
    static final Logger logger = LogManager.getLogger(ParkingLotLatestSpace.class);

    @Reference
    private ParkingLotService parkingLotService;
    @Reference
    private ParkingLotLatestSpaceService parkingLotLatestSpaceService;
    @Reference
    private InParkingService inParkingService;

    //获取某一月份的天数
    public int getMonthDay(int month){
        Date date=null;
        Calendar cale = null;
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, -(0));
        cale.set(Calendar.DAY_OF_MONTH, 2);
        String firstday = format.format(cale.getTime());
        try {
            date=format.parse(firstday);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        return day;
    }

    public long getDayNumber(int month,int day){
        Date date=null;
        Calendar cale = null;
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, -(month));
        cale.set(Calendar.DAY_OF_MONTH,day);
        String firstday = format.format(cale.getTime());
        try {
            date=format.parse(firstday);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime()/1000;
    }

    @RequestMapping(value = "/parkinglots/{project_id}/fixation/tructspace",method = RequestMethod.GET)
    @ResponseBody
    public Response<?> findRegularBookSpaceRate(HttpServletRequest request,
                                                @PathVariable("project_id") String project_id){
        try {


            int j = 0, i = 0, day = 0, dayNumber = 0, dayNumberSum = 0, regular_book_space_sum = 0;
            double this_month = 0, one_month = 0, two_month = 0, three_month, four_month = 0, five_month = 0;
            List<Double> regular_book_space_rate = new ArrayList<>();
            Calendar cale = null;
            InParking inParking = new InParking();
            ParkingLotLatestSpace parkingLotLatestSpaceResult = new ParkingLotLatestSpace();
            for (i = 0; i < 6; i++) {
                if (i == 0) {
                    cale = Calendar.getInstance();
                    day = cale.get(Calendar.DAY_OF_MONTH);
                } else {
                    day = getMonthDay(i);
                }
                for (j = 1; j < day; j++) {
                    ParkingLot parkingLot = new ParkingLot();
                    parkingLot.setProject_code(project_id);
                    List<ParkingLot> parkingLotList = parkingLotService.findAll(parkingLot, null);
                    for (ParkingLot parkinglot : parkingLotList) {
                        ParkingLotLatestSpace parkingLotLatestSpace = new ParkingLotLatestSpace();
                        parkingLotLatestSpace.setParking_id(parkinglot.getId());
                        parkingLotLatestSpace = parkingLotLatestSpaceService.findEntityOne(parkingLotLatestSpace);
                        if (parkingLotLatestSpace != null) {
                            regular_book_space_sum += parkingLotLatestSpace.getRegular_book_space();
                            inParking.setParking_id(parkinglot.getId());
                        }
                        List<InParking> inParkingList = inParkingService.findAll(inParking, null);

                        for (InParking inparking : inParkingList) {
                            if (TimeUtil.getNumerical(inparking.getIn_time()) > getDayNumber(i, (j - 1)) &&
                                    TimeUtil.getNumerical(inparking.getIn_time()) < getDayNumber(i, (j + 1))) {
                                if (dayNumber > inparking.getRemain_fix_num()) {
                                    dayNumber = inparking.getRemain_fix_num();
                                }
                            }
                        }

                    }
                }
                dayNumberSum += dayNumber;
                switch (i) {
                    case 0:
                        this_month = dayNumberSum / (regular_book_space_sum * day);
                        parkingLotLatestSpaceResult.setThis_month(this_month);
                        break;
                    case 1:
                        one_month = dayNumberSum / (regular_book_space_sum * day);
                        parkingLotLatestSpaceResult.setOne_month(one_month);
                        break;
                    case 2:
                        two_month = dayNumberSum / (regular_book_space_sum * day);
                        parkingLotLatestSpaceResult.setTwo_month(two_month);
                        break;
                    case 3:
                        three_month = dayNumberSum / (regular_book_space_sum * day);
                        parkingLotLatestSpaceResult.setThree_month(three_month);
                        break;
                    case 4:
                        four_month = dayNumberSum / (regular_book_space_sum * day);
                        parkingLotLatestSpaceResult.setFour_month(four_month);
                        break;
                    case 5:
                        five_month = dayNumberSum / (regular_book_space_sum * day);
                        parkingLotLatestSpaceResult.setFive_month(five_month);
                        break;
                }
            }
            return ResponseUtil.success(parkingLotLatestSpaceResult);
        }catch (Exception e) {
            logger.error("查询实例列表异常：", e);
            Response<Void> resp = ResponseUtil.bizError(ParkingLotLatestSpaceController.InstanceErrorCode.UNKNOW_ERROR);
            resp.setMsg(e.getMessage());
            resp.setStatus(404);
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
