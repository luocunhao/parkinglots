package cn.xlink.parkinglots.client.requestBodyBean;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
/**
 *https://{xlink_host}/v2/parks/parkinglots/{parkinglot_id}/parking_space-info
 *接口对应的bean
 * */
@Data
@Setter
@Getter
public class ParkingSpaceInfoBean implements Serializable{
    @NotBlank
    private String sn;
    @NotBlank
    private int all_parking_space_count;
    @NotBlank
    private int free_parking_space_count;
}
