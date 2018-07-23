package cn.xlink.parkinglots.client.requestBodyBean;

import lombok.Data;

@Data
public class AddParkingLot {
    /**产品ID(绑定后不可更改)*/
    private String product_id;
    /** 停车场ID(手动输入，全平台唯一) * */
    private String parkinglot_id;
    /** 停车场的名称* */
    private String name;
}
