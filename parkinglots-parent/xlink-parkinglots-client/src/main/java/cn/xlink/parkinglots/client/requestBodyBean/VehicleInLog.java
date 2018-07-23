package cn.xlink.parkinglots.client.requestBodyBean;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

@Data
@Getter
@Setter
public class VehicleInLog implements Serializable{
    @NotBlank
    private String event_sn;
    @NotBlank
    private String gate_sn;
    @Min(1)
    @Max(6)
    private int status;
    @NotBlank
    private String in_time;
    @Min(0)
    @Max(2)
    private int authorization_method;
    @NotBlank
    private String ic_no;
    @NotBlank
    private String car_no;
    @Max(1)
    @Min(0)
    private int pay_method;
}
