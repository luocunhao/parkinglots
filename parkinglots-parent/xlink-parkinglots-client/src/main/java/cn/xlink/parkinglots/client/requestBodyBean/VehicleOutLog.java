package cn.xlink.parkinglots.client.requestBodyBean;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.DecimalMin;
import java.io.Serializable;
@Data
@Getter
@Setter
public class VehicleOutLog extends VehicleInLog implements Serializable {
    @NotBlank
    private String charging_time;
    @NotBlank
    private String out_time;
    @DecimalMin(value = "0.00")
    private double pay_money;
}
