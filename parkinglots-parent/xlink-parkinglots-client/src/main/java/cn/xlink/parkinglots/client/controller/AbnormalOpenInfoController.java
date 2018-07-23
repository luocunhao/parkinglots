package cn.xlink.parkinglots.client.controller;

import cn.xlink.parkinglots.api.domain.AbnormalOpenInfo;
import cn.xlink.parkinglots.api.domain.BarrierGate;
import cn.xlink.parkinglots.api.domain.BarrierGateLatestStatus;
import cn.xlink.parkinglots.api.domain.ParkingLot;
import cn.xlink.parkinglots.api.exception.ErrorCode;
import cn.xlink.parkinglots.api.response.Response;
import cn.xlink.parkinglots.api.response.ResponseUtil;
import cn.xlink.parkinglots.api.service.AbnormalOpenInfoService;
import cn.xlink.parkinglots.api.service.ParkingLotService;
import cn.xlink.parkinglots.api.util.TimeUtil;
import cn.xlink.parkinglots.client.permission.AccessToken;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/v2/parks")
public class AbnormalOpenInfoController {

    static final Logger logger = LogManager.getLogger(AbnormalOpenInfoController.class);

    @Reference
    AbnormalOpenInfoService abnormalOpenInfoService;

    @Reference
    ParkingLotService parkingLotService;

    //3.5开闸原因统计
    @RequestMapping(value = "/parkinglots/gate/open_info/{project_id}/list", method = RequestMethod.GET)
    @ResponseBody
    public Response<?> findGateOpenInfo(HttpServletRequest request,
                                        @PathVariable("project_id") String project_id) {
//        AccessToken accessToken = (AccessToken) request.getAttribute("token");
//        String token = accessToken.getAccessToken();
        try {
            //通过项目id查询所有的停车场
            ParkingLot parkingLot = new ParkingLot();
            parkingLot.setProject_code(project_id);
            List<ParkingLot> all = parkingLotService.findAll(parkingLot, null);
            int unlicensed_count_month = 0;
            int other_count_month = 0;
            int hardware_count_month = 0;
            if (all != null && all.size() > 0) {

                for (int i = 0; i < all.size(); i++) {
                    String parking_code = all.get(i).getId();
                    //通过停车场id查询开闸原因
                    AbnormalOpenInfo abnormalOpenInfo = new AbnormalOpenInfo();
                    abnormalOpenInfo.setParking_id(parking_code);
                    List<AbnormalOpenInfo> allinfo = abnormalOpenInfoService.findAll(abnormalOpenInfo, null);

                    if (allinfo != null && allinfo.size() > 0) {

                        for (int j = 0; j < allinfo.size(); j++) {
                            String open_date = allinfo.get(j).getOpen_date();
                            long open_time = TimeUtil.getNumerical(open_date);
                            //获取当前时间秒数
                            Date date = new Date();
                            long time = date.getTime() / 1000;
                            String abnormal_open_type = allinfo.get(j).getAbnormal_open_type();

                            if (time - open_time < 60 * 60 * 24 * 30) {
                                if (abnormal_open_type.equals("0")) {
                                    unlicensed_count_month++;
                                }
                                if (abnormal_open_type.equals("1")) {
                                    other_count_month++;
                                }
                                if (abnormal_open_type.equals("2")) {
                                    hardware_count_month++;
                                }
                            }
                        }
                    } else {
                        Response<Void> response = ResponseUtil.bizError("500", "所查询的停车场异常开闸记录不存在");
                        response.setStatus(500);
                        return response;
                    }
                }

            }
            JSONObject data = new JSONObject();
            data.put("other_count_month", other_count_month);
            data.put("unlicensed_count_month", unlicensed_count_month);
            data.put("hardware_count_month", hardware_count_month);

            return ResponseUtil.success(data);
        } catch (Exception e) {
            logger.error("查询实例列表异常：", e);
            Response<Void> resp = ResponseUtil.bizError(AbnormalOpenInfoController.InstanceErrorCode.UNKNOW_ERROR);
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
