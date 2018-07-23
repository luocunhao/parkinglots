package cn.xlink.parkinglots.client.requestBodyBean;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
/**
 *https://{xlink_host}/v2/parks/parkinglots/{parkinglot_id}/vehicle-out
 *接口对应的bean
 * */
@Data
@Getter
@Setter
public class VehicleOutLogBean implements Serializable {
    @NotBlank
    private String sn;
    @NotNull
    @Valid
    private List<VehicleOutLog> gates;
}
