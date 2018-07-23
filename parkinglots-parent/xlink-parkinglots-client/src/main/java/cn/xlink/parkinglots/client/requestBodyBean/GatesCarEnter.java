package cn.xlink.parkinglots.client.requestBodyBean;

import lombok.Data;

@Data
public class GatesCarEnter {
   /**车牌*/
    private String car_no;
    /**IC卡号*/
    private String ic_no;
    /**请求返回的结果数组下标偏移量，默认为0*/
    private int offset;
    /**请求返回结果根据offset开始的条目数，默认为10*/
    private int limit;
}
